def call(Map config = [:]){
    buildPlatfrom([gitBranch: 'production',gitRepoUrl:"https://github.com/ALLINBYTE-COMPANY/tool-dropship-api.git"] + (config ?: [:]))
}