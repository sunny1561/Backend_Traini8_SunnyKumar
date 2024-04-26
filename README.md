assignment has done on Intellij idea (spring boot)

only Two classes are there

1.Model 
2.TrainiController 

Interface 
TrainiRepo (to map the class to relatioal database)(ORM)


also make sure database I connected locally so adjust for your(add maven dependency and configuration in properties file)

#(All apis are in the conroller only)

application listening on port 8080
so basic url
 first run server then hit these on postman
get all training_center(get mapping)
#http://localhost:8080/get_centers

create new center along with validating email,phone number,center_code (post mapping)
#http://localhost:8080/create_center

below if you want to get list of all filtered trainingcenter based on key value (get mapping)
#http://localhost:8080/GetAllCenterUsingFilter?filter=key:value









