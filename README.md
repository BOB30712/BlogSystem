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
