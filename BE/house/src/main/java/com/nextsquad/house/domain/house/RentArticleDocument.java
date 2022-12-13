package com.nextsquad.house.domain.house;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Document(indexName = "#{@elasticSearchConfig.getIndexName()}")
public class RentArticleDocument {

    @Id
    private Long id;

    @Field(type = FieldType.Text, name = "title")
    private String title;
    @Field(name = "is_completed")
    private boolean isCompleted;
    @Field(name = "is_deleted")
    private boolean isDeleted;

}
