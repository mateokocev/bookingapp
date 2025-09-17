import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
    state: () => ({
        user: null as { email: string; firstName: string; lastName: string } | null
    }),
    actions: {
        async fetchUser() {
        try {
            const data = await $fetch<{ email: string; firstName: string; lastName: string }>(
            '/api/users/me',
            { credentials: 'include' }
            )
            this.user = data
            localStorage.setItem('auth_user', JSON.stringify(data))
        } catch (err) {
            this.user = null
            localStorage.removeItem('auth_user')
        }
        },
        async logout() {
        try {
            await $fetch('/api/users/logout', { method: 'POST', credentials: 'include' })
        } finally {
            this.user = null
            localStorage.removeItem('auth_user')
        }
    }
    }
})