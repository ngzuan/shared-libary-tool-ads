def call(Map config = [:]){
    buildPlatfrom([gitBranch: 'spy-production',gitRepoUrl:"https://github.com/ALLINBYTE-COMPANY/shopify-app-tracking.git",services:["frontend"]] + (config ?: [:]))
}