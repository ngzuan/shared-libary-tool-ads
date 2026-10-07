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

        steps.withCredentials([steps.usernamePassword(
            credentialsId: cfg.registryCredId,
            usernameVariable: 'REGISTRY_USER',
            passwordVariable: 'REGISTRY_PASS',
        )]) {
            steps.sh """
                echo "${REGISTRY_PASS}" | docker login ${cfg.registryHost} -u "${REGISTRY_USER}" --password-stdin
            """
        }
        steps.env."IMAGE_TAG_${serviceName.replace('-', '_').toUpperCase()}" = tag
        steps.echo "Pushed ${image}"
    }
}