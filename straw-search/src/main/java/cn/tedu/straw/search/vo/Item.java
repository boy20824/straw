package cn.tedu.straw.search.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@Slf4j
@NoArgsConstructor  //創建無參數建構子
@AllArgsConstructor //創建全部參數的建構子
//spring ES API 會自動在ES中創建items索引
@Document(indexName = "items")
public class Item implements Serializable {
    @Id
    private Long id;
    @Field(type= FieldType.Text,analyzer = "ik_smart",searchAnalyzer = "ik_smart")
    private String title;
    @Field(type = FieldType.Keyword)
    private String category;
    @Field(type = FieldType.Keyword)
    private String band;
    @Field(index = false,type = FieldType.Keyword)
    private String image;
    @Field(type = FieldType.Double)
    private Double price;
}
