<template>
    <a-card
        class="antd-pro-pages-dashboard-analysis-salesCard"
        :loading="loading"
        :bordered="false"
        :title="$t('detect.tobacco.user')"
        :style="{ height: '100%' }"
    >
        <div slot="extra" style="height: inherit;">
            <span class="dashboard-analysis-iconGroup">
                <a-dropdown :trigger="['click']" placement="bottomLeft">
                    <a-icon type="ellipsis" class="ant-dropdown-link"/>
                    <a-menu slot="overlay">
                        <a-menu-item>
                            <a href="javascript:">{{ $t('dashboard.analysis.dropdown-option-one') }}</a>
                        </a-menu-item>
                        <a-menu-item>
                            <a href="javascript:">{{ $t('dashboard.analysis.dropdown-option-two') }}</a>
                        </a-menu-item>
                    </a-menu>
                </a-dropdown>
            </span>
        </div>
        <div>
            <mini-sector :scale="scale" :data-source="dataSource" :sectorStyle="sectorStyle" :height="height"></mini-sector>
        </div>
    </a-card>
</template>

<script>
import MiniSector from '@/components/Charts/MiniSector.vue'

export default {
    name: 'Sector',
    components: {
        MiniSector
    },
    props: {
        dataSource: {
            type: Array,
            required: true
        },
        scale: {
            type: [Object, Array],
            required: true
        },
        sectorStyle: {
            type: [Object, Array],
            default: () => {
                return {
                    stroke: '#fff',
                    lineWidth: 1
                }
            }
        },
        height: {
            type: Number,
            default: 300
        },
        loading: {
            type: Boolean,
            default: false
        }
    },
    data() {
        return {
        }
    }
}
</script>
