# GoudaChess

When you clone *Eszter-v2* branch, these are the quidelines to make the program work:

### First of all, you will need the ***users* database**, to get it you can **run the commands from the attached *vaalikone.sql*** which is located in the *database* folder. 
Then you need to make further configurations in eclipse:

## I. Import the EclipseLink (again):

1. right click on the project: **select Properties**
    
    
2. on the *Properties for vaalikones* window

    * search for **"Project Facets"** on the left hand side (under JPA if it isn't visible by default)

    * **unselect JPA**, then click "Apply" 
   
    * **select JPA again**, then **click on the warning** which appears at the bottom of the window **("Further configuration required...")**
   
3. on the *Modify Faceted Project* window 

    * next to the white box on the right hand side there are two icon - **select the floppy disk icon**
   
        * on the *Download Library* window look for ***EclipseLink 2.5.2* - select it**

        * you might have for Library name: *EclipseLink 2.5.2 (1)* - in this case **delete the *(1)* to overwrite the file** and click "Next"

        * **accept the Licence**, then click "Finish" (it might take a while to download the library)

    * you are supposed to be again on the *Modify Faceted Project* window - under the Connection **click    *Add connection...***

        * on the *New Connection Profile* window select *MySQL*, you may give name to the connection, then click "Next"

        * set the driver: next to the dropdown menu (on the top of the window) **click on the circle icon**

            * on the *New Driver Definition* window **select the MySQL with the System Version 5.1** (last one from the list)

            * give a Driver name(important to remember to that name for the step 2!), then (on this window still!) **select the "JAR List" tab** (at the top of the window)

            * select the jar file which is already there, and remove it ("Remove JAR/Zip" - button on the right)

            * **click Add JAR/Zip...** and select the jar file which the project folder (GoudaChess-Eszter-v2) already includes - Open it, then click "OK"

        * under the properties **change the "database" to vaalikone** (this step assumes that you have vaalikone database working in mySQL) 

        * change the "database" to vaalikone in the URL as well

        * give your password (and user name) for mySQL
        
        * you may select "Save password", after that you may click "Test Connection" button to make sure your connection is successful

        * if "Ping succeeded" you can **click "Finish"**

    * you are again on the *Modify Faceted Project* window - under *Persist class management* **select "Discover annotated classes automatically"**

    * click "OK"

    -> now you are supposed to revert back to the ***Properties for vaalikones* window**


## II. Fix the Driver:

 * on *Properties for vaalikones* window **go to *Build Path*** (search for it on the left)

    * click on the *Libraries* tab and **click on *MySQL JDBC Drivers*** and click *"Edit..."* (on the right hand side)

        * in the "Available Driver Definition" dropdown menu select the previously created driver(the Driver name from step 1)


## III. Make sure you don't have any server running in eclipse, and try to run the program!


*Good luck!* :octocat:  :metal: 
