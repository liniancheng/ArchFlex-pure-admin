<template>
    <div>
        <v-chart :forceFit="true" :height="height" :data="dataSource">
            <v-tooltip dataKey="item*percent"/>
            <v-coord type="polar" :innerRadius="0.2"/>
            <v-sector position="item*percent" color="item" :vStyle="sectorStyle"/>
            <v-legend dataKey="item"/>
        </v-chart>
    </div>
</template>

<script>
export default {
    name: 'MiniSector',
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
        }
    },
    data() {
        return {
        }
    }
}
</script>
