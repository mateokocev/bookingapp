<template>
  <div
    class="min-h-screen bg-gray-900 text-white"
    style="background-image: radial-gradient(rgba(255, 255, 255, 0.1) 1px, transparent 1px); background-size: 16px 16px;"
  >
    <header class="border-b border-gray-700">
      <nav class="container mx-auto px-4 sm:px-6 lg:px-8 flex justify-between items-center h-16">
        <div class="flex items-center space-x-6">
          <NuxtLink to="/" class="text-2xl font-bold text-emerald-500 transition-all duration-300 hover:drop-shadow-[0_0_6px_#34d399]">
            MyBooking
          </NuxtLink>

          <!-- BUTTONI PORED NAVBARA -->
          
          <!-- <div class="hidden md:flex items-center space-x-2">
            <UButton
              icon="i-heroicons-building-office-2"
              label="Stays"
              variant="ghost"
              size="sm"
              class="text-gray-300 hover:text-white hover:bg-gray-800"
            />
            <UButton
              icon="i-heroicons-paper-airplane"
              label="Flights"
              variant="ghost"
              size="sm"
              class="text-gray-300 hover:text-white hover:bg-gray-800"
            />
            <UButton
              icon="i-mdi-car"
              label="Car rental"
              variant="ghost"
              size="sm"
              class="text-gray-300 hover:text-white hover:bg-gray-800"
            />
          </div> -->

        </div>

        <div v-if="!authStore.user" class="flex items-center space-x-3">
          <UButton
            label="Register"
            variant="outline"
            size="sm"
            class="text-white border-gray-600 hover:bg-gray-800"
            to="auth/signup"
          />
          <UButton
            label="Sign in"
            variant="solid"
            size="sm"
            class="text-gray-900 font-bold"
            to="auth/signin"
          />
        </div>
        <div v-else class="flex items-center space-x-4">
          <span class="text-sm text-gray-300">
            Hi, {{ authStore.user.name }}
          </span>
          <UButton
            icon="i-heroicons-arrow-left-on-rectangle"
            label="Logout"
            variant="ghost"
            size="sm"
            class="text-gray-300 hover:text-white hover:bg-gray-800"
            @click="handleLogout"
          />
        </div>
      </nav>
    </header>

    <main class="container mx-auto px-4 sm:px-6 lg:px-8">
      <div class="max-w-4xl mx-auto text-center py-20 md:py-32">
        <h1 class="text-4xl md:text-5xl font-bold text-white">
          Find your next stay
        </h1>
        <p class="text-lg text-gray-400 mt-4">
          Search deals on hotels, homes, and much more...
        </p>

        <form @submit.prevent="handleSearch" class="mt-10">
          <div
            class="bg-gray-800/75 border border-gray-700 backdrop-blur-xl rounded-lg p-3"
          >
            <div class="grid grid-cols-1 md:grid-cols-10 gap-3 items-center">
              <div class="md:col-span-3">
                <UInput
                  v-model="searchQuery.destination"
                  icon="i-heroicons-map-pin"
                  size="xl"
                  placeholder="Where are you going?"
                  :ui="{ leadingIcon: 'text-gray-400' }"
                />
              </div>

              <div class="md:col-span-3">
                <UInput
                  v-model="searchQuery.dates"
                  icon="i-heroicons-calendar-days"
                  size="xl"
                  placeholder="Check-in — Check-out"
                  :ui="{ leadingIcon: 'text-gray-400' }"
                />
              </div>
              
              <div class="md:col-span-3">
                <UInput
                  v-model="searchQuery.occupancy"
                  icon="i-heroicons-user-group"
                  size="xl"
                  placeholder="2 adults · 0 children"
                  :ui="{ leadingIcon: 'text-gray-400' }"
                />
              </div>

              <div class="md:col-span-1">
                <UButton
                  type="submit"
                  label="Search"
                  size="xl"
                  block
                  class="text-gray-900 font-bold"
                />
              </div>
            </div>
          </div>
        </form>
        <ListingCard class="mt-2"/>
      </div>
    </main>

    </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import { useAuthStore } from '~/stores/auth'

const authStore = useAuthStore()
const router = useRouter()

// Reaktilno stanje za podatke iz search bara
const searchQuery = reactive({
  destination: '',
  dates: '',
  occupancy: ''
})

// Funkcija koja se poziva na submit forme
function handleSearch() {
  console.log('Searching with:', searchQuery)

}

async function handleLogout() {
  await authStore.logout()

  await navigateTo('/')
}
</script>

<style>

body :scope{
  background-color: #111827; /* bg-gray-900 */
  color: #ffffff; /* text-white */
}
</style>