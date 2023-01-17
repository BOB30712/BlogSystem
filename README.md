# BlogSystem
![image](https://github.com/BOB30712/BlogSystem/blob/main/BlogSystemRED.png)    
      
        
## 2023年01月17日
問題處理:Article與Target兩個Entity有外鍵連結(ManyToMany),結果當時刪除其中一列Article的資料時,連帶相關的Target與Article都刪除了。         
處理方式:將原先@ManyToMany(cascade = CascadeType.ALL)改成@ManyToMany(cascade = CascadeType.PERSIST)     
相關資料:      
CascadeType.PERSIST 在儲存時一併儲存被參考的物件。       
CascadeType.MERGE 在合併修改時一併合併修改被參考的物件。      
CascadeType.REMOVE 在移除時一併移除被參考的物件。        
CascadeType.REFRESH 在更新時一併更新被參考的物件。        
CascadeType.ALL 無論儲存、合併、更新或移除，一併對被參考物件作出對應動作。         
