# ElasticSearch

是一款**分布式全文检索引擎**，底层基于**Lucene**实现

用Java编写，提供简单易用的RESTFul API,屏蔽复杂性，让搜索更简单

轻松横向扩展，可支持PB级的结构化或非机构化数据处理





## **应用场景：**

- 海量数据分析引擎
- 站内搜索引擎
- 数据存储仓库

一线公司实际应用：

英国卫报：实时分析公众对文章的回应

维基百科，GitHub：站内实时搜索

百度：实时日志监控平台

## ES版本：

版本历史：1.x -> 2.x -> 5.x

因ES产品包括ELK，各个产品更新速度不一样。之后为了统一管理，ELK版本统一到5.x

## ES vs Solr

- solr查询死数据时，相对es更快些。但是数据若是实时改变的，es会更优秀
- 集群搭建：solr依赖第三方插件，如zookeeper；es本身就支持集群搭建，不需要第三方
- es社区更丰富
- es对当前的大数据支持更友好

## 倒排索引：

- 将存放的数据以一定方式进行分词，将分词的内容存放在一个单独的分词库中
- 做查询时，先将查询的内容进行分词
- 去分词库中匹配你内容，最终得到数据的id标识
- 根据id去存放数据的位置取到指定的数据



## **ES存储结构：**

### Index 索引

> - 可以创建多个索引(index)
> - 每个索引默认被分成5个分片存储(shared)
> - 每个分片都会有至少一个备份分片
>   - 备份分片不会帮助检索数据，除非es压力特别大时
>   - 备份分片必须放到不同服务器中



### Type 类型

- > 一个索引(index)下可以有多个类型(type)
  >
  > - 5.x 版本，一个index 下可以创建多个type
  > - 6.x 版本，一个index 创建一个type
  > - 7.x 版本，一个index 下没有type

  ​	

### Doc 文档

> 一个类型中(Type)，可以有多个document

### Field 属性

> 一个文档(document)中包含多个属性(Field)

## ES的Restful语法

### Get请求

> http://ip:port/index  									查询索引信息
>
> http://ip:port/index/type/doc_id   				查询文档

### Post请求

> http://ip:port/index/type/_search                 请求体添加JSON **查询条件** 来查询相应文档
>
> http://ip:port/index/type/doc_id/_update     请求体中添加JSON **修改文档**

### Put请求

> http://ip:port/index                                  创建索引，请求体中需要指定索引的信息，type，field
>
> http://ip:port/index/type/_mappings         创建索引，指定索引文档存储的属性信息

### Delete请求

> http://ip:port/index                        删除index
>
> http://ip:port/index/type/doc_id     删除指定文档





## 创建

### 创建索引

> ```json
> PUT /person
> {
> "settings": {
>  "number_of_shards": 5,   //指定分片数量，默认5  
>  "number_of_replicas": 1    //指定副本数据，默认1  
> }
> }
> ```
>
> 

### 查看索引

> GET /索引名

删除索引

> delete /索引名

## Field可被指定的类型

> 字符串类型：
>
> - text                                  text类型一般用于全文检索，将当前Filed进行分词
> - keyword                           keyword一般指定不会变化的string，如地名。keyword的field不会被分词
>
> 数值类型
>
> - long
> - integer
> - short
> - byte
> - double
> - float
> - half_float                       精度为float的一半，即16位
> - scaled_float                   指定精度，根据一个long类型和scaled_float来指定一个浮点型
>   - ​												e.g  long->456 ,     scaled->100     =  4.56
>
> 时间类型
>
> - ​	可以指定具体的格式
>
> 布尔类型
>
> - 表达 true 和false
>
> 二进制类型
>
> - binary类型 暂时支持Base64 编码的字符串
>
> 范围类型
>
> - long_range: 赋值时，不需指定具体内容，只需存储一个范围即可，指定 gt,  lt,  gte,  lte
> - integer_range:   同上
> - double_range:   同上
> - float_range:       同上
> - date_range:       同上
> - ip_range:           同上
>
> 经纬度类型
>
> - geo_point:    用来存储经纬度
>
> IP类型
>
> - 可以存储IPv4 ,IPv6
>
> 其他类型 请参考文档https://www.elastic.co/guide/en/elasticsearch/reference/6.5/mapping-types.html
>
> 

## 创建索引并指定结构

```json
PUT /book
{
  "settings": {
    "number_of_shards": 5,    //指定分片数
    "number_of_replicas": 1    //指定备份数
  },
    //指定数据结构
  "mappings": {	
      //类型 type
    "novel": {
        // 文档存储的Field
      "properties": {
          //属性名
        "name": {
            //类型
          "type": "text",
            //指定分词器
          "analyzer": "ik_max_word",
            //当前的field是否作为查询条件
          "index": true,
            //是否需要额外的存储
          "store": false
        },
        "author": {
          "type": "keyword"
        },
        "count": {
          "type": "long"
        },
        "on-sale": {
          "type": "date",
            // 时间类型执行格式化方式   epoch_millis允许毫秒值
          "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis"
        }
      }
    }
  }
}
```

## 文档的操作

> 在ES中定位一个文档： `_index`,  `_type`,  `_id`    

### 新建文档document

- ### 自动生成id方式

  ```json
  POST /book/novel
  {
    "name":"倚天屠龙记",
    "author":"古龙",
    "count":100000,
    "on-sale":"2000-15-15",
    "descr":"武侠小说"
  }
  ```

  

- ### 手动指定id

```json
POST /book/novel/1
{
  "name":"红楼梦",
  "author":"曹雪芹",
  "count":100000,
  "on-sale":"2020-14-10",
  "descr":"中国古典文学名著"
}		
```

- ### 修改文档

  - 覆盖式

    ​	添加文档，手动指定id

    ```json
    PUT /book/novel/1
    {
      "name":"红楼梦",
      "author":"曹雪芹",
      "count":"45645",
      "on-sale":"2012-04-05",
      "descr":"机阿里山的房间里"
    }
    ```

    

  - #### doc修改方式

    ```json
    POST /book/novel/1/_update
    {
      "doc": {
        "count":"123456"   //指定需要修改的field
      }
    }
    ```

    

- ### 删除文档

  - ```json
    	DELETE /book/novel/文档id
    ```

    

  

## java操作ES

- ### 连接esCLient

  - 导包

    - ```xml
      <dependency>
          <groupId>org.elasticsearch</groupId>
          <artifactId>elasticsearch</artifactId>
          <version>6.5.4</version>
      </dependency>
      <dependency>
          <groupId>org.elasticsearch.client</groupId>
          <artifactId>elasticsearch-rest-high-level-client</artifactId>
          <version>6.5.4</version>
      </dependency>
      ```

    - 编写esClient工具类

      - ​	

        ```java
        public class ESClient {
        
            public static RestHighLevelClient getESClient(){
                HttpHost httpHost = new HttpHost("127.0.0.1",9200);
                RestClientBuilder clientBuilder = RestClient.builder(httpHost);
                RestHighLevelClient ESclient = new RestHighLevelClient(clientBuilder);
                return ESclient;
            }
        }		
        
        ```

        

  ​	

- ## 创建索引index

  - ```java
     @Test
        public void createIndex() throws IOException {
            String index = "person";
            String type = "man";
            //准备index需要的settings和mappings
            Settings.Builder settings = Settings.builder()
                    .put("number_of_shards", 3)
                    .put("number_of_replicas", 1);
            //mappings
            XContentBuilder mappings = JsonXContent.contentBuilder()
                    .startObject()
                        .startObject("properties")
                            .startObject("name")
                                .field("type", "text")
                            .endObject()
      
                            .startObject("age")
                                .field("type", "long")
                            .endObject()
      
                            .startObject("birthday")
                                .field("type", "date")
                                .field("format", "yyyy-MM-dd")
                            .endObject()
                        .endObject()
                    .endObject();
      
            //将mappings和settings封装到CreateIndexRequest对象
            CreateIndexRequest request = new CreateIndexRequest(index)
                    .settings(settings)
                    .mapping(type, mappings);
      
            //通过esclient连接es并创建索引
            CreateIndexResponse response = esClient.indices().create(request);
            		
    ```

    ## 查看索引是否存在

    ```java
    @Test
    public void exists() throws IOException {
        GetIndexRequest request = new GetIndexRequest();
        request.indices(index);
        boolean exists = esClient.indices().exists(, RequestOptions.DEFAULT);
        System.out.println(exists);
    }
    ```

    ## 删除索引

    ```java
    @Test
    public void deleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest();
        request.indices(index);
        AcknowledgedResponse delete = esClient.indices().delete(request);
        System.out.println(delete);
    }
    ```

    ## 添加doc

    jackson依赖

    ```xml
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.10.3</version>
    </dependency>
    ```

    POJO

    ```java
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Person {
    
        @JsonIgnore
        private Integer id;
    
        private String name;
        private Integer age;
    
        @JsonFormat(pattern = "yyyy-MM-dd")
        private Date birthday;
    }	
    
    ```

    

    ```java
    @Test
        public void addDoc() throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            Person person = new Person(2, "王五", 12, new Date());
            String json = mapper.writeValueAsString(person);
            //准备一个request对象
            IndexRequest request = new IndexRequest(index,type,person.getId().toString());
            request.source(json, XContentType.JSON);
            IndexResponse response = esClient.index(request, RequestOptions.DEFAULT);
            System.out.println(response.getResult().toString());
        }
    ```

    ## 修改文档

    ```java
     @Test
        public void updateDoc() throws IOException {
            //创建map，指定修改内容
            Map<String,Object> doc = new HashMap<>();
            doc.put("name","大大强");
            String docId = "2";
            //创建request，封装数据
            UpdateRequest request = new UpdateRequest(index,type,docId);
            //修改内容放入request
            request.doc(doc);
            //通过ESClient对象执行request
            UpdateResponse update = esClient.update(request, RequestOptions.DEFAULT);
            System.out.println(update.getResult().toString());
        }
    ```

    ## 删除文档

    ```java
     @Test
        public void deleteDoc() throws IOException {
            //要删除的docId
            String docId = "3";
            //创建request对象
            DeleteRequest request = new DeleteRequest(index,type,docId);
            //通过ESClient执行
            DeleteResponse delete = esClient.delete(request, RequestOptions.DEFAULT);
            System.out.println(delete.getResult().toString());
        }	
        
    ```

    

    ## java批量操作文档

    ### 	批量添加文档

    ​		

    ```Java
    @Test
        public void batchAdd() throws IOException {
            //对象转json
            ObjectMapper mapper = new ObjectMapper();
            //准备添加数据
            Person p1 = new Person(3, "a", 23, new Date());
            Person p2 = new Person(4, "b", 24, new Date());
            Person p3 = new Person(5, "c", 21, new Date());
            String json1 = mapper.writeValueAsString(p1);
            String json2 = mapper.writeValueAsString(p2);
            String json3 = mapper.writeValueAsString(p3);
            //准备request对象
            BulkRequest request = new BulkRequest();
            request.add(new IndexRequest(index,type,p1.getId().toString()).source(json1,XContentType.JSON));
            request.add(new IndexRequest(index,type,p2.getId().toString()).source(json2,XContentType.JSON));
            request.add(new IndexRequest(index,type,p3.getId().toString()).source(json3,XContentType.JSON));
            //esClient执行request
            BulkResponse response = esClient.bulk(request, RequestOptions.DEFAULT);
            System.out.println(response.toString());
        }
    ```

    

## ES各种查询

### term & terms

#### term查询

> ​	term查询是代表完全匹配，对搜索的条件不会进行分词。
>
> ​		e.g  查询  省份 为 “北京” ，那么 “北京”这个查询条件是不会被分词的

```json
POST /sms-logs-index/_search
{
  "from": 0,  #分页，从第几页开始
  "size": 2,  # 每页内容多少
  "query": {
    "term": {
      "province": {
        "value": "北京"
      }
    }
  }
}
```

Java代码：

```java
 public void termQuery() throws IOException {
        //指定request
        SearchRequest request = new SearchRequest(index);
        request.types(type);
        //指定query条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.from(0);
        builder.size(3);
        SearchSourceBuilder query = builder.query(QueryBuilders.termQuery("province", "北京"));
        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            String source = hit.getSourceAsString();
            System.out.println(source);
        }
    }
```

#### Terms查询

> terms和term查询机制一样，不会对查询条件分词。terms查询可以**指定多个查询值**

```json
POST /sms-logs-index/_search
{
  "from": 0,
  "size": 20, 
  "query": {
    "terms": {
      "province": [
        "北京"
        "上海"
      ]
    }
  }
}
```

Java代码

```Java
@Test
    public void termsQuery() throws IOException{
        //request
        SearchRequest request = new SearchRequest(index );
        request.types(type);
        //query条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.from(0);builder.size(20);
        builder.query(QueryBuilders.termsQuery("province","北京","天津"));
        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            String source = hit.getSourceAsString();
            System.out.println(source);
        }
    }
```

### match查询

match查询属于高层查询，会根据查询的字段类型不一样，采用不同的查询方式

- 查询的是日期或者数值的话，会将查询内容转换为日期或者数值
- 若查询的内容类型是不能分词（keyword），match查询不会对查询的条件分词
- 若查询的内容为可分词内容（text），match查询将查询条件进行分词，去分词库中匹配查询内容
- match查询底层是多个term查询，将多个term查询的结果封装到一起



#### 		match_all查询

​			match_all查询，查询全部内容，不指定任何查询条件

```json
POST /sms-logs-index/_search 
{
  "query": {
    "match_all": {}
  }
}
```

java

```java
@Test
    public void matchAll() throws IOException {
        SearchRequest request = new SearchRequest(index);
        request.types(type);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());
        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }
        System.out.println(hits.length);
    }	
```

#### match查询

```json
POST /sms-logs-index/_search
{
  
  "query": {
    "match": {
      "smsContent":{
          "query":"test data 2",  // test and data
          "operator": "and"
      }
    }
  }
}
```

```java 
@Test
public void matchSmsLogs() throws IOException {
    SearchRequest request = new SearchRequest(index);
    request.types(type);
    SearchSourceBuilder builder = new SearchSourceBuilder();
    builder.query(QueryBuilders.matchQuery("smsContent","test data").operator(Operator.AND));
    request.source(builder);
    SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
    SearchHit[] hits = response.getHits().getHits();
    for (SearchHit hit : hits) {
        Map<String, Object> sourceAsMap = hit.getSourceAsMap();
        System.out.println(sourceAsMap);
    }
    System.out.println(hits.length);
}
```

#### multiMatch查询

对查询的条件可以指定多个Field

```json
POST /sms-logs-index/_search
{
  "query":{
    "multi_match": {
      "query": "联通",
      "fields": ["smsContent","corpName"]
    }
  }
}
```

```java
 @Test
    public void multiMatchSmsLogs() throws IOException {
        SearchRequest request = new SearchRequest(index);
        request.types(type);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.multiMatchQuery("test","smsContent","name")); // 查询内容，查询字段，字段可以为多个(可变参数)
        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            Map<String, Object> source = hit.getSourceAsMap();
            System.out.println(source);
        }
    }
```

### 其他查询方式

#### 根据id查询

```json
GET /sms-logs-index/sms-logs-type/1      #indix,type,id
```

```java
@Test
public void queryById() throws IOException {
    GetRequest request = new GetRequest(index, type, "1"); //创建getRequest对象
    GetResponse response = esClient.get(request,RequestOptions.DEFAULT);//client直接get
    Map<String, Object> source = response.getSourceAsMap();
    System.out.println(source);
}
```

#### 根据多个id查询

```json
POST /sms-logs-index/_search
{
  "query": {
    "ids": {
      "values": [1,2,3]
    }
  }
}		
```

```java
@Test
public void queryByIds() throws IOException {
    SearchRequest request = new SearchRequest(index);
    request.types(type);
    //指定查询条件
    SearchSourceBuilder builder = new SearchSourceBuilder();
    builder.query(QueryBuilders.idsQuery().addIds("1","2","3"));//这里
    request.source(builder);
    //client查询
    SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
    SearchHit[] hits = response.getHits().getHits();
    for (SearchHit hit : hits) {
        Map<String, Object> source = hit.getSourceAsMap();
        System.out.println(source);
    }
}
```

#### prefix查询

通过一个查询条件去指定一个field的前缀，从而查询到指定的文档

```json
POST /sms-logs-index/_search
{
  "query": {
    "prefix": {
      "smsContent": {
        "value": "data"
      }
    }
  }
}
```

```java
@Test
public void prefixQuery() throws IOException {
    SearchRequest request = new SearchRequest(index);
    request.types(type);
    SearchSourceBuilder builder = new SearchSourceBuilder();
    builder.query(QueryBuilders.prefixQuery("smsContent","test"));
    request.source(builder);
    SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
    SearchHit[] hits = response.getHits().getHits();
    for (SearchHit hit : hits) {
        System.out.println(hit.getSourceAsMap());
    }
}
```

#### 模糊查询fuzzy

```json
POST /sms-logs-index/_search
{
  "query": {
    "fuzzy": {
      "smsContent": {
        "value": "data",
        "prefix_length": 0  //指定前面n个字符不允许出现错误
      }
    }
  }
}
```

```Java
@Test
public void fuzzyQuery() throws IOException {
    SearchRequest request = new SearchRequest(index);
    request.types(type);
    SearchSourceBuilder builder = new SearchSourceBuilder();
    builder.query(QueryBuilders.fuzzyQuery("smsContent","test").prefixLength(0));
    request.source(builder);
    SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
    SearchHit[] hits = response.getHits().getHits();
    for (SearchHit hit : hits) {
        System.out.println(hit.getSourceAsMap());
    }
}
```

#### wildcard查询 wildcard:通配符

```json
POST /sms-logs-index/_search
{
  "query": {
    "wildcard": {
      "smsContent": {
        "value": "*2"
      }
    }
  }
}

```

```
@Test
public void wildcardQuery() throws IOException {
    SearchRequest request = new SearchRequest(index);
    request.types(type);
    SearchSourceBuilder builder = new SearchSourceBuilder();
    builder.query(QueryBuilders.wildcardQuery("smsContent","*2"));
    request.source(builder);
    SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
    SearchHit[] hits = response.getHits().getHits();
    for (SearchHit hit : hits) {
        System.out.println(hit.getSourceAsMap());
    }
}
```

#### 范围查询range

```json
POST /sms-logs-index/_search
{
  "query": {
    "range":{
      "fee": {
        "gte": 1,    //大于等于，Grete Than or Equals to
        "lte": 3	 //小于等于，Less Than or Equals to
      }
    }
  }
}
```

#### 正则查询 regexp

```json
POST /sms-logs-index/sms-logs-type/_search
{
  "query": {
    "regexp": {
      "mobile": "156[0-9]{6}[1-2]{1}"
    }
  }
}
```

### 深分页 scroll

ES对from + size 这种分页有限制，from+size < 1W

#### **from + size 分页 分页原理：**

> 1. 将用户指定的关键词进行分词
> 2. 拿分词去分词库进行检索，得到多个文档document id
> 3. 去各分片中拉去指定数据 【耗时较长】
> 4. 将拉取到的数据根据得分 score进行排序 【耗时较长】
> 5. 舍弃不符合 from 条件的
> 6. 返回结果
>
> #### **Scroll + size分页原理：**
>
> 1. 将搜索关键词分词
>
> 2. 将分词去分词库中进行检索，得到多个document id
>
> 3. 将 文档 document id 存放在es上下文中 （内存中）
>
> 4. 根据size的个数去es中检索指定size个数的数据，拿到的数据从es上下文中删除
>
> 5. 若需要下一页，从es上下文中找下一页数据
>
> 6. 循环第四步 和 第五步
>
>    
>
>    **因scroll + size 分页方式 数据从上下文中得到，所以不适合做实时查询**

```json
# scroll + size 分页
POST /sms-logs-index/sms-logs-type/_search?scroll=1m    # _search 后面带上 scroll,1m表示查询到的数据在内存中保存1分钟
{
  "query": {
    "match_all": {}
  },
  "size": 2,
  "sort": [
    {
      "fee": {
        "order": "desc"
      }
    }
  ]
}

# scroll + size 查询下一页内容
POST /_search/scroll
{
"scroll_id":"DnF1ZXJ5VGhlbkZldGNoBQAAAAAAAHWSFk1RNnJmY1lkVEJHQlpVaElWSjJEbEEAAAAAAAB1kxZNUTZyZmNZZFRCR0JaVWhJVkoyRGxBAAAAAAAAdZQWTVE2cmZjWWRUQkdCWlVoSVZKMkRsQQAAAAAAAHWVFk1RNnJmY1lkVEJHQlpVaElWSjJEbEEAAAAAAAB1lhZNUTZyZmNZZFRCR0JaVWhJVkoyRGxB",
  "scroll":"1m"     #每次查询下一页时带上数据在内存中存放的时间，不然查不到数据
}


# 删除 scroll 
DELETE /_search/scroll/DnF1ZXJ5VGhlbkZldGNoBQAAAAAAAHWSFk1RNnJmY1lkVEJHQlpVaElWSjJEbEEAAAAAAAB1kxZNUTZyZmNZZFRCR0JaVWhJVkoyRGxBAAAAAAAAdZQWTVE2cmZjWWRUQkdCWlVoSVZKMkRsQQAAAAAAAHWVFk1RNnJmY1lkVEJHQlpVaElWSjJEbEEAAAAAAAB1lhZNUTZyZmNZZFRCR0JaVWhJVkoyRGxB

```

```Java
@Test
public void scrollTest() throws IOException {
    //request
    SearchRequest request = new SearchRequest(index);
    request.types(type);
    //指定scroll信息
    request.scroll(TimeValue.timeValueMinutes(2l));
    //指定查询条件
    SearchSourceBuilder builder = new SearchSourceBuilder();
    builder.size(2);
    builder.sort("fee", SortOrder.DESC);
    builder.query(QueryBuilders.matchAllQuery());
    request.source(builder);
    //获取返回结果，scrollId，source
    SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
    String scrollId = response.getScrollId();
    SearchHit[] hits = response.getHits().getHits();
    System.out.println("----------- 首页 -----------");
    for (SearchHit hit : hits) {
        System.out.println(hit.getSourceAsMap().get("smsContent"));
    }
    
    //循环获取下一页数据
    while (true) {
        //创建scrollRequest
        SearchScrollRequest request1 = new SearchScrollRequest(scrollId);
        request1.scroll(TimeValue.timeValueMinutes(1l));  //每次设置scrollID 时间
        SearchResponse scrResp = esClient.scroll(request1, RequestOptions.DEFAULT);
        SearchHit[] hits1 = scrResp.getHits().getHits();
        if (hits1 != null && hits1.length > 0) {
            System.out.println("----------- 下一页 -----------");
            for (SearchHit hit : hits1) {
                System.out.println(hit.getSourceAsMap().get("smsContent"));
            }
        } else {
            System.out.println("----------- END -----------");
            break;
        }
    }
    
    //清除scroll
    ClearScrollRequest csr = new ClearScrollRequest();
    csr.addScrollId(scrollId);
    esClient.clearScroll(csr,RequestOptions.DEFAULT);
}
```

### delete-by-query

通过查询方式(term，terms，range,match 等) 删除文档 document

