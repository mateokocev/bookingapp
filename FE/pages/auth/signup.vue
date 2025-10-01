<template>
  <div
    class="min-h-screen flex items-center justify-center bg-gray-900 py-12"
    style="background-image: radial-gradient(rgba(255, 255, 255, 0.1) 1px, transparent 1px); background-size: 16px 16px;"
  >
    <UCard class="max-w-md w-full bg-gray-800/75 border border-gray-700 backdrop-blur-xl">
      <div class="space-y-6">
        <div class="text-center">
          <div class="mx-auto h-16 w-16 bg-gray-700 rounded-lg mb-4" />
          <h1 class="text-2xl font-bold text-white">Create an account</h1>
          <p class="text-sm text-gray-400">Please enter your details to sign up.</p>
        </div>

        <UForm :state="state" @submit="onSubmit" class="space-y-4">
          <div class="grid grid-cols-2 gap-4">
            <UFormField name="name">
              <template #label><span class="sr-only">Name</span></template>
              <UInput v-model="state.name" placeholder="Name" size="lg" class="w-full" />
            </UFormField>

            <UFormField name="surname">
              <template #label><span class="sr-only">Surname</span></template>
              <UInput v-model="state.surname" placeholder="Surname" size="lg" class="w-full" />
            </UFormField>
          </div>

          <UFormField name="email">
            <template #label><span class="sr-only">Email</span></template>
            <UInput v-model="state.email" placeholder="Enter your email..." size="lg" class="w-full" />
          </UFormField>

          <UFormField name="password">
            <template #label><span class="sr-only">Password</span></template>
            <UInput
              v-model="state.password"
              type="password"
              placeholder="Password"
              size="lg"
              class="w-full"
            />
          </UFormField>

          <UFormField name="passwordConfirm">
            <template #label><span class="sr-only">Confirm Password</span></template>
            <UInput
              v-model="state.passwordConfirm"
              type="password"
              placeholder="Confirm Password"
              size="lg"
              class="w-full"
            />
          </UFormField>

          <UButton
            type="submit"
            label="Sign Up"
            size="lg"
            block
            class="mt-6 bg-emerald-500 hover:bg-emerald-600 text-gray-900 font-bold"
          />
        </UForm>

        <p class="text-sm text-center text-gray-400">
          Already have an account?
          <ULink to="/auth/signin" class="font-semibold text-emerald-500 hover:text-emerald-600">
            Sign In
          </ULink>
        </p>
      </div>
    </UCard>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import type { FormSubmitEvent } from '#ui/types'
import { useAuthStore } from '~/stores/auth'

definePageMeta({
  layout: 'auth'
})

const config = useRuntimeConfig()
const toast = useToast()
const auth = useAuthStore()

const state = reactive({
  name: '',
  surname: '',
  email: '',
  password: '',
  passwordConfirm: ''
})

async function onSubmit(event: FormSubmitEvent<any>) {
  if (event.data.password !== event.data.passwordConfirm) {
    toast.add({
      title: 'Error',
      description: 'Passwords do not match',
      color: 'error'
    })
    return
  }

  try {
    await $fetch(`${config.public.apiBase}/users/register`, {
      method: 'POST',
      body: {
        name: event.data.name,
        surname: event.data.surname,
        email: event.data.email,
        password: event.data.password
      },
      credentials: 'include'
    })

    await auth.fetchUser()

    toast.add({
      title: 'Success',
      description: 'Account created successfully!',
      color: 'success'
    })

    await navigateTo('/')
  } catch (err: any) {
    toast.add({
      title: 'Registration failed',
      description: err?.data?.message || 'Unable to create account',
      color: 'error'
    })
  }
}
</script>
