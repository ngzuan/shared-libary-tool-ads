def call(Map config = [:]){
    def label="master"
    node(label){
        setupParameters()

        def cfg = com.toolads.PipelineConfig.mergeDefaults(config)
        def services =config.services

        stage('Checkout') {
            checkout scm
            env.GIT_COMMIT = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()
        }

        services.each { svc ->
            stage("Build ${svc}") {
                    com.toolads.DockerBuilder.buildAndPush(this, cfg, svc)
            }
        }
    }
}