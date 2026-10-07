package com.toolads

class PipelineConfig implements Serializable {

    static final Map SERVICES = [
        'api': [
            dockerfile  : 'docker/Dockerfile-api',
            context     : '.',
            snapshotMode: 'full',
        ],
        'worker': [
            dockerfile  : 'docker/Dockerfile-internal-consumer',
            context     : '.',
            snapshotMode: 'full',
        ],
        'proxy': [
            dockerfile  : 'docker/Dockerfile-proxy',
            context     : '.',
            snapshotMode: 'full',
        ],
        'frontend': [
            dockerfile  : 'Dockerfile',
            context     : 'frontend',
            snapshotMode: 'time',
        ],
    ]

    static Map mergeDefaults(Map user) {
        def defaults = [
            registryHost       : 'ghcr.io',
            registryProject    : 'toolads',
            gitBranch          : 'production',
            gitRepoUrl         : 'https://github.com/ALLINBYTE-COMPANY/tool-dropship-api.git',
        ]
        return defaults + user
    }
}