package B1G4.bookmark.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RestClient;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import java.io.IOException;
import java.time.Duration;


@Configuration
@RequiredArgsConstructor
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris}")
    private String elasticsearchUrl;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticsearchUrl.replace("http://", ""))
                .withConnectTimeout(Duration.ofSeconds(5))
                .withSocketTimeout(Duration.ofSeconds(30))
                .build();
    }

    @PostConstruct
    public void createIndex() {
        try {
            String[] hostAndPort = elasticsearchUrl.replace("http://", "").split(":");
            RestClient restClient = RestClient.builder(
                            new HttpHost(hostAndPort[0], Integer.parseInt(hostAndPort[1]), "http"))
                    .setRequestConfigCallback(requestConfigBuilder ->
                            requestConfigBuilder
                                    .setConnectTimeout(5000)
                                    .setSocketTimeout(30000))
                    .build();

            // 인덱스 존재 여부 확인 시 재시도 로직 추가
            int maxRetries = 3;
            int retryCount = 0;
            boolean success = false;

            while (!success && retryCount < maxRetries) {
                try {
                    Request getRequest = new Request("GET", "/jaso");
                    restClient.performRequest(getRequest);
                    success = true;
                } catch (IOException e) {
                    if (retryCount == maxRetries - 1) {
                        // 마지막 시도에서 인덱스 생성
                        Request createRequest = new Request("PUT", "/jaso");
                        createRequest.setJsonEntity("""
                            {
                              "settings": {
                                "index": {
                                  "analysis": {
                                    "filter": {
                                      "suggest_filter": {
                                        "type": "edge_ngram",
                                        "min_gram": 1,
                                        "max_gram": 50
                                      }
                                    },
                                    "tokenizer": {
                                      "jaso_search_tokenizer": {
                                        "type": "jaso_tokenizer",
                                        "mistype": true,
                                        "chosung": false
                                      },
                                      "jaso_index_tokenizer": {
                                        "type": "jaso_tokenizer",
                                        "mistype": true,
                                        "chosung": true
                                      }
                                    },
                                    "analyzer": {
                                      "suggest_search_analyzer": {
                                        "type": "custom",
                                        "tokenizer": "jaso_search_tokenizer"
                                      },
                                      "suggest_index_analyzer": {
                                        "type": "custom",
                                        "tokenizer": "jaso_index_tokenizer",
                                        "filter": [
                                          "suggest_filter"
                                        ]
                                      }
                                    }
                                  }
                                }
                              }
                            }
                            """);
                        restClient.performRequest(createRequest);
                        success = true;
                    }
                    retryCount++;
                    Thread.sleep(2000); // 2초 대기
                }
            }

            restClient.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Elasticsearch index", e);
        }
    }
}