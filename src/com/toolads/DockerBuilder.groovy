package com.toolads

class DockerBuilder implements Serializable {

    static void buildAndPush(def steps, Map cfg, String serviceName) {
        def meta = PipelineConfig.SERVICES[serviceName]
        if (!meta) {
            steps.error("Unknown service: ${serviceName}")
        }
        def tag = steps.env.GIT_COMMIT?.take(7) ?: 'latest'
        def image = "${cfg.registryHost}/${cfg.registryProject}/${serviceName}:${tag}"
        def cacheRepo = "${cfg.registryHost}/${cfg.registryProject}/cache/${serviceName}"

        docker.withRegistry("${cfg.registryHost}", "${cfg.registryCredId}") {
            steps.echo "Pushing ${image}"
            def builtImage = docker.build("${image}")
            builtImage.push()
        }

        steps.env."IMAGE_TAG_${serviceName.replace('-', '_').toUpperCase()}" = tag
        steps.echo "Pushed ${image}"
    }
}