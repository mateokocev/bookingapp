
<template>
  <div
    class="w-full bg-gray-800/70 border border-gray-700 rounded-2xl shadow-lg overflow-hidden flex"
  >
    <!-- Left Content -->
    <div class="flex-1 p-6 flex flex-col justify-between max-w-2xl">
      <!-- Title + Location -->
      <div>
        <h2 class="text-xl font-semibold text-white leading-snug hover:text-emerald-400 transition">
          {{ title }}
        </h2>
        <div class="text-sm text-gray-400 mt-1">
          {{ location }} · {{ distance }}
        </div>

        <div class="mt-3 text-sm text-gray-300">
          {{ details }}
        </div>
      </div>

      <!-- Ratings + Price -->
      <div class="mt-4 space-y-3">
        <div class="flex items-center justify-between">
          <div class="text-sm">
            <span class="text-emerald-400 font-semibold">{{ ratingLabel }}</span>
            <span class="text-gray-400 ml-1">({{ reviews }} reviews)</span>
          </div>
          <div class="bg-emerald-500 text-gray-900 font-bold px-2 py-1 rounded-lg">
            {{ rating }}
          </div>
        </div>

        <!-- Price Section -->
        <div class="flex items-center justify-between">
          <div v-if="oldPrice" class="text-sm text-gray-400 line-through">
            € {{ oldPrice }}
          </div>
          <div class="text-xl font-bold text-white">
            € {{ price }}
          </div>
        </div>

        <UButton
          label="See availability"
          size="md"
          block
          class="bg-emerald-500 text-gray-900 font-bold hover:bg-emerald-400"
        />
      </div>
    </div>

    <!-- Right Image Carousel -->
    <div class="relative w-1/2 h-auto">
      <img
        :src="images[currentImage]"
        alt="Listing photo"
        class="w-full h-full object-cover transition-all duration-500"
      />

      <!-- Prev Button -->
      <button
        @click="prevImage"
        class="absolute top-1/2 left-2 -translate-y-1/2 bg-gray-900/50 p-2 rounded-full hover:bg-emerald-500/70"
      >
        ‹
      </button>

      <!-- Next Button -->
      <button
        @click="nextImage"
        class="absolute top-1/2 right-2 -translate-y-1/2 bg-gray-900/50 p-2 rounded-full hover:bg-emerald-500/70"
      >
        ›
      </button>

      <!-- Dots -->
      <div class="absolute bottom-2 left-1/2 -translate-x-1/2 flex space-x-1">
        <span
          v-for="(img, i) in images"
          :key="i"
          class="w-2 h-2 rounded-full"
          :class="i === currentImage ? 'bg-emerald-400' : 'bg-gray-500/70'"
        ></span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue"

const props = defineProps({
  title: { type: String, default: "Spacious Old-School Condo in Maksimir" },
  location: { type: String, default: "Maksimir, Zagreb" },
  distance: { type: String, default: "2.1 km from downtown" },
  details: { type: String, default: "Entire apartment · 1 bedroom · 1 bathroom · 1 kitchen · 29 m²" },
  rating: { type: Number, default: 7.8 },
  ratingLabel: { type: String, default: "Good" },
  reviews: { type: Number, default: 5 },
  oldPrice: { type: Number, default: 1294 },
  price: { type: Number, default: 1195 },
  images: {
    type: Array as () => string[],
    default: () => [
      "https://picsum.photos/800/600?1",
      "https://picsum.photos/800/600?2",
      "https://picsum.photos/800/600?3",
    ]
  }
})

const currentImage = ref(0)

function nextImage() {
  currentImage.value = (currentImage.value + 1) % props.images.length
}

function prevImage() {
  currentImage.value =
    (currentImage.value - 1 + props.images.length) % props.images.length
}
</script>