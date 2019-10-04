import groovy.json.JsonSlurper
import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials;
import jenkins.model.Jenkins

List<String> listofTags4Img = new ArrayList<String>()

try {
    def jsonSlurper = new JsonSlurper()
    def n = 101 
    def dockerRepositoryUrl = "https://repository.docker.image/v2/"+nameofImage+"/tags/list?n="+n.toString()
    def isAll="no"      //not exist do while in groovy
    while(isAll!=""){  
        def dockerRepositoryUrltmp = "https://repository.docker.image/v2/"+nameofImage+"/tags/list?n="+n.toString() 
        isAll= ["curl -X GET -I ${dockerRepositoryUrltmp} | grep next"].execute().text;
        if(isAll != "")
            n=n+1
    } 
    dockerRepositoryUrl = "https://repository.docker.image/v2/"+nameofImage+"/tags/list?n="+n.toString()
    def listofTags4ImgObjectRaw = [" curl -X GET -H ${dockerRepositoryUrl}"].execute().text;
    def listofTags4ImgJsonObject = jsonSlurper.parseText(listofTags4ImgObjectRaw)
    def dataArray = listofTags4ImgJsonObject.tags
    for(item in dataArray){        
        listofTags4Img.add(item)
    }
    listofTags4Img=listofTags4Img.reverse()
}
catch (Exception e) {
    listofTags4Img.add(e)
}
return listofTags4Img