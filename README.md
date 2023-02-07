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
1. 專案按右鍵選擇RUN As->Maven bulid
2. 在Goals欄位clean package
3. 點擊RUN(大約1分鐘，結束會在terminal出現Final Memory)
4. jar檔會產生在Spring Boot\target的資料夾底下
5. 到Spring Boot\target的資料夾位置，打開cmd
6. 輸入java -jar 專案名稱.jar
7. 結束執行程式(按下Ctrl+C)  

