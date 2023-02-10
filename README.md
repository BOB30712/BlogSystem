# BlogSystem
![image](https://github.com/BOB30712/BlogSystem/blob/main/BlogSystemRED.png)    
      
        
## 2023年01月17日
* 問題處理:
  * Article與Target兩個Entity有外鍵連結(ManyToMany),結果當時刪除其中一列Article的資料時,連帶相關的Target與Article都刪除了。         
* 處理方式:
  * 將原先@ManyToMany(cascade = CascadeType.ALL)改成@ManyToMany(cascade = CascadeType.PERSIST)     
* 相關資料:      
  * CascadeType.PERSIST 在儲存時一併儲存被參考的物件。       
  * CascadeType.MERGE 在合併修改時一併合併修改被參考的物件。      
  * CascadeType.REMOVE 在移除時一併移除被參考的物件。        
  * CascadeType.REFRESH 在更新時一併更新被參考的物件。        
  * CascadeType.ALL 無論儲存、合併、更新或移除，一併對被參考物件作出對應動作。   

## 2023年01月18日
```
//外掛文字編輯器(CKEditor 5)套用到vue
 <template>
   <ckeditor :editor="editor" v-model="editorData" :config="editorConfig"></ckeditor>
 </template>

 <script>
  import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
  export default {
    data(){
     return {
      editor: ClassicEditor,
      editorData: '<p>Content of the editor.</p>',
      editorConfig: {},
     }
    } 
  }
 </script>
```
      
## 2023年01月19日
* 問題(前端、JavaScript):
  * VUE的reader.onload方法怎麼把值拋出去?
原先要透過reader.onload將上傳的圖片同
步顯示在畫面上，但一直無法在方法內將
圖片轉換後的數值傳到v-bind綁定img的src
標籤之中

* 解決方式:
  * 原先的reader.onload = function (e)用法因為
作用域的問題，不能使用this指定屬性或方法，
需要改成reader.onload = e => {}，才能達到
目的。(原因和Javascript箭頭函式有關，this
會被綁訂在全域環境)    
      
## 2023年01月31日
* 後端錯誤處理:刪除標籤時回饋錯誤SQLIntegrityConstraintViolationException
  * 原文:java.sql.SQLIntegrityConstraintViolationException: Cannot delete or update a parent row: a foreign key constraint
  * 原因:資料庫的target的tid與arttar的tid之間有外鍵約束
* 後續處理:
```
//原先作法:mappedBy依據Article.java的targets屬性，無法設定CascadeType.PERSIST單獨刪除單一target資料
//只能設定CascadeType.ALL同時刪除對應的article資料
//透過fetch = FetchType.LAZY設定可以除非真正要使用到該屬性的值，否則不會真正將資料從表格中載入物件，但在 findAll()似乎被忽略
@ManyToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY, mappedBy="targets")
private Set<Article> articles;

//後續作法:比照Article.java的連接方式就可以設定CascadeType.PERSIST單一刪除target資料
@ManyToMany(cascade = CascadeType.PERSIST)
@JoinTable(name="arttar", joinColumns = {@JoinColumn(name="tid")}, inverseJoinColumns = {@JoinColumn(name="aid")})
private Set<Article> articles;
```    
      
## 2023年02月07日
Spring Boot打包成jar檔  
* 方法一  
1. 專案按右鍵選擇RUN As->Maven bulid
2. 在Goals欄位clean package
3. 點擊RUN(大約1分鐘，結束會在terminal出現Final Memory)
4. jar檔會產生在Spring Boot\target的資料夾底下
5. 到Spring Boot\target的資料夾位置，打開cmd
6. 輸入java -jar 專案名稱.jar
7. 結束執行程式(按下Ctrl+C)  
* 方法二  
1. 專案按右鍵選擇RUN As->Maven clean
2. 專案按右鍵選擇RUN As->Maven install   
        
## 2023年02月08日
Windows 10 安裝WSL2
1. 啟用"Windows 子系統Linux版"功能   
  流程:控制台->程式集->程式和功能->"點選"開啟或關閉Windows功能->"勾選"Windows 子系統Linux版
2. 啟用虛擬機器功能   
  流程:"以管理員身分開啟"PowerShell->"輸入"dism.exe /online /enable-feature /featurename:VirtualMachinePlatform /all /norestart->"重啟電腦"
3. 下載Linux核心更新套件    
  流程:"到Windows官方教學(https://docs.microsoft.com/zh-tw/windows/wsl/install-win10)" ->"點選"舊版的手動安裝->"找到步驟4"+"點擊下載最新套件"WSL2 Linux 核心更新套件 (適用於 x64 電腦)  
        
補充:   
* 檢查目前檢查目前運行的WSL    
  * 流程:"打開"PowerShell->"輸入"wsl --list --verbose或wsl -l -v      
* Docker Containers命名     
  * 如果未指定Container名稱，Docker會隨機指定名稱例如:crazy jones    
* Dockerimage檔相關指令   
  * 創建檔案:docker build . -t docker-demo-app
  * 查詢目前存在的image檔案: docker images
  * 執行container: docker run -p 3000:3000 -it <your-image-id> 
  * 關閉container:ctrl + c或是開啟另外一個 terminal，然後透過指令 docker ps 找到運行中的 Container ID，然後在輸入 docker stop <ContainerID>
  * 刪除image檔案:docker rmi <your-image-id>  

## 2023年02月10日 
在Docker上啟動Spring boot專案並且連上MYSQL container
1. 安裝mysql的image
2. 建立Spring Boot與MYSQL使用的網路    
> 步驟:
> 1. '打開'命令提示字元
> 2. '輸入'docker network create <網路名稱>
> 3. '輸入'docker network ls(顯示所有網路)    
3. 建立mysql的container   
> 在命令提示字元'輸入'docker container run -p <port位置>:<port位置> --name <container名稱> --network <網路名稱> -e MYSQL_ROOT_PASSWORD=<密碼> -e MYSQL_DATABASE=<資料庫名稱> -d mysql:<mysql版本>     
4. 修改Spring Boot的application.properties設定
```
spring.datasource.url=jdbc:mysql://<網路名稱>/<資料庫名稱>?serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.http.encoding.force=true

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
//備註:未設定allowPublicKeyRetrieval=true，會有
SQLNonTransientConnectionException: Public Key Retrieval is not allowed錯誤訊息
```
5. 將Spring Boot打包成jar檔
> 1. 專案按右鍵選擇RUN As->Maven clean
> 2. 專案按右鍵選擇RUN As->Maven install 
> 3. 出現錯誤訊息(因為尚未做成image檔案，也沒有產生container，所以不能透過<網路名稱>連線MYSQL的container)
>>方法處理:讓jar檔產生過程不去做測試，修改pom.xml
```
<build>
  <plugins>
    <plugin>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-maven-plugin</artifactId>
    </plugin>
    <plugin> <!-- start -->
        <artifactId>maven-surefire-plugin</artifactId>
        <version>2.22.2</version>
        <configuration>
            <includes>
                <include>TestFail.java</include>
            </includes>
            <testFailureIgnore>true</testFailureIgnore>
        </configuration>
    </plugin> <!-- end -->
  </plugins>
  
  <finalName>BlogSystem</finalName> <!-- 設定產生的jar檔案名稱 -->
</build>
```
6. 產生Spring Boot專案的image
>1. 移動到專案的資料夾的target，可以看到要包裝成image的jar檔
>2. '新增'檔案並'命名'為Dockerfile，並且沒有任何副檔名(exc:'.txt')
>>Dockerfile內容
>>>FROM openjdk:17-jdk-alpine     
>>>COPY <jar檔案> app.jar       
>>>ENTRYPOINT ["java","-jar","app.jar"]     
>3. '打開'命令提示字元
>4. '輸入'docker build . -t <image檔名>產生的image檔案   
      
7. 藉由步驟6產生的image檔案製作container
>1. '打開'命令提示字元
>2. '輸入'docker container run --network <網路名稱> --name <container名稱> -p <port位置>:<port位置> -d <SpringBoot image的id>  
      
8. 檢查container狀況
>'開啟'Docker Desktop先後將MYSQL、Spring Boot的container啟動
>'開啟'命令提示字元'輸入'docker logs -f <container名稱或是id> 
     
* 常用docker指令
```shell
//從Docker Hub下載image
docker pull mysql

//透過dockerdile產生image
docker build . -t docker-demo-app

//檢查目前有那些image檔
docker images

//透過-it後面的image id產生container
docker run -p 3000:3000 -it 733776b1db0a

//檢查目前有那些container正在執行
docker ps

//檢查目前有那些container
docker ps -a

//停止container
docker stop mysql8

//刪除container
docker rm mysql8

//開啟mysql的container，並進入mysql
docker exec -it <container名稱> <image名稱> -u root -p

//進入mysql後
create database DevDb; -- 創建資料庫
show databases; -- 顯示資料庫
use DevDb; -- 使用資料庫
quit -- 離開資料庫

//檢視container在Docker預設的bridge network的IP位址
docker network inspect bridge
```
