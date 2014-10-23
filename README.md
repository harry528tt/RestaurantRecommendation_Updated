Use MySQL, Jsoup, HTML, JavaScript Regular Expression to realize a menu recommendation api.

1. Parse informations from restaurant web sites;
2. Store the infomation into local database with JDBC;
3. Retrive the related menus and restaurants according to the key words given by customers. 
    (e.g. Please type in the key word:  Salad;  
          Here is the recommendations we found for you:
            Chicken Pakora --------- $ 9.55  from   Bombay Garden;
            Chicken soup with bread ------------- $ 11.29  from WestPalace;
            ......
    )
