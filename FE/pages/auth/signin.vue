<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-900" style="background-image: radial-gradient(rgba(255, 255, 255, 0.1) 1px, transparent 1px); background-size: 16px 16px;">
    
    <UCard class="max-w-sm w-full bg-gray-800/75 border border-gray-700 backdrop-blur-xl">
      <div class="space-y-6">
        <div class="text-center">
          <div class="mx-auto h-16 w-16 bg-gray-700 rounded-lg mb-4" />
          
          <h1 class="text-2xl font-bold text-white">
            Welcome back
          </h1>
          <p class="text-sm text-gray-400">
            Please enter your details to sign in.
          </p>
        </div>

        <div class="grid grid-cols-3 gap-3">
          <UButton icon="i-simple-icons-apple" aria-label="Apple" size="lg" variant="outline" class="w-full justify-center" />
          <UButton icon="i-simple-icons-google" aria-label="Google" size="lg" variant="outline" class="w-full justify-center" />
          <UButton icon="i-simple-icons-twitter" aria-label="Twitter" size="lg" variant="outline" class="w-full justify-center" />
        </div>

        <UForm :state="state" @submit="onSubmit" class="space-y-4">
 
            <UFormField  name="email">
              <template #label><span class="sr-only">Email</span></template>
              <UInput v-model="state.email" placeholder="Enter your email..." size="lg" class="w-full"/>
            </UFormField>
        
            <UFormField name="password">
              <template #label><span class="sr-only">Password</span></template>
              <UInput
                v-model="state.password"
                type="password"
                placeholder="••••••••••"
                size="lg"
                class="w-full"
              />
            </UFormField>
        
          <UButton 
            type="submit" 
            label="Sign in" 
            size="lg" 
            block
            class="!mt-6 bg-emerald-500 hover:bg-emerald-600 text-gray-900 font-bold"
          />
        </UForm>

        <p class="text-sm text-center text-gray-400">
          Don't have an account yet?
          <ULink to="/auth/signup" class="font-semibold text-emerald-500 hover:text-emerald-600">
            Sign Up
          </ULink>
        </p>
      </div>
    </UCard>
  </div>
</template>

<script setup lang="ts">
import { reactive } from 'vue'
import type { FormSubmitEvent } from '#ui/types'

definePageMeta({
  layout: 'auth'
})

const state = reactive({
  email: '',
  password: ''
})

async function onSubmit (event: FormSubmitEvent<any>) {
  console.log(event.data)
}
</script>