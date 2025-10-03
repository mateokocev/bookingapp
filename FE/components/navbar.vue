<template>
  <header class="border-b border-gray-700">
    <nav
      class="container mx-auto px-4 sm:px-6 lg:px-8 flex justify-between items-center h-16"
    >
      <div class="flex items-center space-x-6">
        <NuxtLink
          to="/"
          class="text-2xl font-bold text-emerald-500 transition-all duration-300 hover:drop-shadow-[0_0_6px_#34d399]"
        >
          MyBooking
        </NuxtLink>
      </div>

      <div v-if="!authStore.user" class="flex items-center space-x-3">
        <UButton
          label="Register"
          variant="outline"
          size="sm"
          class="text-white border-gray-600 hover:bg-gray-800"
          to="/auth/signup"
        />
        <UButton
          label="Sign in"
          variant="solid"
          size="sm"
          class="text-gray-900 font-bold"
          to="/auth/signin"
        />
      </div>

      <div v-else class="flex items-center space-x-4">
        <span class="text-sm text-gray-300">
          Hi, {{ authStore.user.name }}
        </span>

        <UDropdownMenu
          :items="dropdownItems"
          :content="{ align: 'end', side: 'bottom', sideOffset: 6 }"
          :ui="{ content: 'w-56' }"
        >
          <UButton
            icon="i-lucide-user"
            variant="ghost"
            size="sm"
            class="text-gray-300 hover:text-white hover:bg-gray-800"
          />
        </UDropdownMenu>
      </div>
    </nav>
  </header>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useAuthStore } from '~/stores/auth'
import type { DropdownMenuItem } from '@nuxt/ui'

const authStore = useAuthStore()

const dropdownItems = ref<DropdownMenuItem[][]>([
  [
    {
      label: authStore.user?.name || 'User',
      type: 'label',
      avatar: { src: 'https://i.pravatar.cc/100' }
    }
  ],
  [
    {
      label: 'Profile',
      icon: 'i-lucide-user',
      to: '/profile'
    },
    {
      label: 'Payment Methods',
      icon: 'i-lucide-credit-card',
      to: '/payments'
    },
    {
      label: 'Booking History',
      icon: 'i-lucide-calendar',
      to: '/bookings'
    }
  ],
  [
    {
      label: 'Logout',
      icon: 'i-lucide-log-out',
      color: 'error',
      onSelect: async () => {
        await authStore.logout()
        await navigateTo('/')
      }
    }
  ]
])
</script>
