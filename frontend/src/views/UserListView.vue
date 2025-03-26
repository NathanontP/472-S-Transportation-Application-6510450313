<template>
    <div class="page-container">
        <Header></Header>

        <div class="main-container">
            <div class="user-container">
                <h2 class="user-title">Users</h2>
                <UserCard
                    v-for="user in users"
                    :key="user.id"
                    :userId="user.id"
                    :name="user.name"
                />
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import { mapGetters } from 'vuex'
import UserCard from '../components/UserCard.vue'
import Header from '../components/Header.vue'
import { apiBaseUrl } from '@/main'

export default {
    data() {
        return {
            users: [],
        }
    },
    created() {
        this.fetchUsers()
    },
    computed: {
        ...mapGetters(['userRole', 'username']),
    },
    methods: {
        async fetchUsers() {
            try {
                const response = await axios.get(`${apiBaseUrl}/users`)
                this.users = response.data
            } catch (error) {
                console.error('Error fetching users:', error)
            }
        },
    },
    components: {
        Header,
        UserCard,
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

.user-title {
    font-family: 'Inter', sans-serif;
    font-size: 24px;
    font-weight: bold;
    color: #333;
    margin-bottom: 20px;
    text-align: left;
}

.user-container {
    background-color: #e6e6e6;
    padding: 20px;
    width: 80%;
    max-width: 1200px;
    overflow-y: auto;
    height: 100%;
}
</style>
