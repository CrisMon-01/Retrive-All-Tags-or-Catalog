import groovy.json.JsonSlurper
import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials;
import jenkins.model.Jenkins

List<String> artifacts = new ArrayList<String>()

try {

    def jsonSlurper = new JsonSlurper()
    def n = 101
    def artifactsUrl = "https://repository.docker.image/v2/"+app+"/tags/list?n="+n.toString()
    def isAll="no"
    while(isAll!=""){  
        def artifactsUrltmp = "https://repository.docker.image/v2/"+app+"/tags/list?n="+n.toString()
        isAll= ["curl -X GET -I ${artifactsUrltmp} | grep next"].execute().text;
        if(isAll != "")
            n=n*2
    } 
    artifactsUrl = "https://repository.docker.image/v2/"+app+"/tags/list?n="+n.toString()
    def artifactsObjectRaw = [" curl -X GET -H ${artifactsUrl}"].execute().text;
    def artifactsJsonObject = jsonSlurper.parseText(artifactsObjectRaw)
    def dataArray = artifactsJsonObject.tags
    for(item in dataArray){
        if (!item.endsWith('-SNAPSHOT')){
            artifacts.add(item)
        }
    }
    artifacts=artifacts.reverse()
}
catch (Exception e) {
    artifacts.add(e)
}
return artifacts