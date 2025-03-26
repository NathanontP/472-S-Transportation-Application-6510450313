<template>
    <div class="page-container">
        <Header></Header>

        <div class="main-container">
            <div class="worker-container">
                <h2 class="worker-title">Workers</h2>
                <WorkerCard
                    v-for="worker in workers"
                    :key="worker.id"
                    :status="worker.status"
                    :workerId="worker.id"
                    :name="worker.name"
                />
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import { mapGetters } from 'vuex'
import Header from '../components/Header.vue'
import WorkerCard from '../components/WorkerCard.vue'
import { apiBaseUrl } from '@/main'

export default {
    data() {
        return {
            workers: [],
        }
    },
    created() {
        this.fetchWorkers()
    },
    components: {
        Header,
        WorkerCard,
    },
    computed: {
        ...mapGetters(['userRole', 'username']),
    },
    methods: {
        async fetchWorkers() {
            try {
                const response = await axios.get(
                    `${apiBaseUrl}/transportation-workers`
                )
                console.log('Response data:', response.data)

                if (Array.isArray(response.data)) {
                    this.workers = response.data.map((worker) => ({
                        id: worker.id,
                        name: worker.name,
                        status: worker.status,
                    }))
                } else {
                    console.warn('Expected an array but got:', response.data)
                    this.workers = []
                }
            } catch (error) {
                console.error('Error fetching workers:', error)
            }
        },
    },
}
</script>

<style scoped>
.page-container {
    background-color: var(--main-bg-color);
    min-height: 100vh;
    display: flex;
    flex-direction: column;
}

.main-container {
    display: flex;
    justify-content: center;
    flex: 1;
}

.worker-title {
    font-size: 24px;
    font-weight: bold;
    color: #333;
    margin-bottom: 20px;
    text-align: left;
}

.worker-container {
    background-color: #e6e6e6;
    padding: 10px;
    width: 80%;
    max-width: 1200px;
    overflow-y: auto;
    height: 100%;
}
</style>
