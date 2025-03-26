<template>
    <Header></Header>
    <div class="container">
        <h1>Create User</h1>
        <div class="create-user-container">
            <form
                @submit.prevent="handleSubmit"
                method="post"
                class="create-user-form"
            >
                <div class="form-group">
                    <label for="username">Username</label>
                    <input
                        type="text"
                        v-model="formData.username"
                        id="username"
                        required
                        placeholder="Enter username"
                    />
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input
                        type="password"
                        v-model="formData.password"
                        id="password"
                        required
                        placeholder="Enter password"
                    />
                </div>
                <div class="form-group">
                    <label for="name">Name</label>
                    <input
                        type="text"
                        v-model="formData.name"
                        id="name"
                        required
                        placeholder="Enter name"
                    />
                </div>
                <div class="form-group">
                    <label for="phoneNumber">Phone Number</label>
                    <input
                        type="text"
                        v-model="formData.phoneNumber"
                        id="phoneNumber"
                        required
                        placeholder="Enter phone number"
                    />
                </div>
                <div class="form-group">
                    <label for="email">E-mail</label>
                    <input
                        type="email"
                        v-model="formData.email"
                        id="email"
                        required
                        placeholder="Enter E-mail"
                    />
                </div>
                <div class="form-group">
                    <label for="role">Role</label>
                    <select v-model="formData.role" id="role" required>
                        <option value="" disabled selected>Select Role</option>
                        <option value="USER">User</option>
                        <option value="WORKER">Worker</option>
                    </select>
                </div>
                <button type="submit" class="accept-button">Accept</button>
            </form>
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Header from '../components/Header.vue'
import { apiBaseUrl } from '@/main'

export default {
    components: {
        Header,
    },
    computed: {
        ...mapGetters(['userRole']),
    },
    data() {
        return {
            formData: {
                username: '',
                password: '',
                name: '',
                phoneNumber: '',
                email: '',
                role: '',
            },
        }
    },
    methods: {
        async handleSubmit() {
            try {
                const response = await fetch(`${apiBaseUrl}/create-user`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(this.formData),
                })

                if (response.ok) {
                    const contentType = response.headers.get('Content-Type')
                    if (
                        contentType &&
                        contentType.includes('application/json')
                    ) {
                        const result = await response.json()
                        alert(result.message)
                    } else {
                        const text = await response.text()
                        alert(text)
                    }
                    this.resetForm()
                } else {
                    const errorText = await response.text()
                    alert(errorText || 'An error occurred. Please try again.')
                }
            } catch (error) {
                console.error('Error:', error)
                alert('An error occurred. Please try again.')
            }
        },
        resetForm() {
            this.formData = {
                username: '',
                password: '',
                name: '',
                phoneNumber: '',
                email: '',
                role: '',
            }
        },
    },
}
</script>

<style scoped>
.container {
    max-width: 70%;
    height: 94vh;
    margin: 0 auto;
    background-color: #f9f9f9;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.create-user-container {
    max-width: 80%;
    height: 88%;
    margin: 0 auto;
    padding: 20px;
    background-color: #f9f9f9;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    display: flex;
    justify-content: center;
    align-items: center;
}

.create-user-form {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
}

.form-group {
    width: 25%;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    margin-bottom: 3%;
}

input {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1);
}

select {
    border-radius: 5px;
}

.accept-button {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 90px;
    height: 40px;
    background-color: #189e1f;
    color: rgb(255, 255, 255);
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

h1 {
    display: inline-block;
    text-align: left;
    margin-top: 5%;
    margin-bottom: 0px;
    margin-left: 10%;
}

label {
    margin-bottom: 5px;
    font-weight: bold;
}

.accept-button {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 90px;
    height: 40px;
    background-color: #189e1f;
    color: rgb(255, 255, 255);
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.accept-button:hover {
    background-color: #24be2c;
}
</style>
