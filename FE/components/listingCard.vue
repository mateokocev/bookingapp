<template>
  <NuxtLink :to="`/listings/${listing.id}`" class="block w-full text-white no-underline">
    <div
      class="w-full bg-gray-800/70 border border-gray-700 rounded-2xl shadow-lg overflow-hidden flex h-full transition-transform duration-300 hover:-translate-y-1 hover:shadow-emerald-500/20"
    >
      <div class="flex-1 p-6 flex flex-col justify-between max-w-2xl">
        <div>
          <h2 class="text-xl font-semibold leading-snug hover:text-emerald-400 transition">
            {{ listing.title }}
          </h2>
          <div class="text-sm text-gray-400 mt-1">
            {{ listing.location }} · {{ listing.distance }}
          </div>
          <div class="mt-3 text-sm text-gray-300">
            {{ listing.details }}
          </div>
        </div>

        <div class="mt-4 space-y-3">
          <div class="flex items-center justify-between">
            <div class="text-sm">
              <span class="text-emerald-400 font-semibold">{{ listing.ratingLabel }}</span>
              <span class="text-gray-400 ml-1">({{ listing.reviews }} reviews)</span>
            </div>
            <div class="bg-emerald-500 text-gray-900 font-bold px-2 py-1 rounded-lg">
              {{ listing.rating }}
            </div>
          </div>
          <div class="flex items-center justify-between">
            <div v-if="listing.oldPrice" class="text-sm text-gray-400 line-through">
              € {{ listing.oldPrice }}
            </div>
            <div velse></div>
            <div class="text-xl font-bold text-white">
              € {{ listing.price }}
            </div>
          </div>
        </div>
      </div>

      <div class="relative w-1/2 h-auto hidden sm:block">
        <img
          :src="listing.images[currentImage]"
          alt="Listing photo"
          class="w-full h-full object-cover"
        />
        </div>
    </div>
  </NuxtLink>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from "vue"

const props = defineProps({
  listing: {
    type: Object,
    required: true
  }
})

const currentImage = ref(0)
let interval: number | null = null;

function startSlideshow() {
  stopSlideshow(); 
  interval = setInterval(() => {
    currentImage.value = (currentImage.value + 1) % props.listing.images.length;
  }, 3000);
}

function stopSlideshow() {
  if (interval) {
    clearInterval(interval);
    interval = null;
  }
}

onMounted(() => {
  // Bonus: automatsko mijenjanje slika kad miš nije na kartici
  // startSlideshow();
});

onUnmounted(() => {
  stopSlideshow();
});
</script>

<style scoped>
/* Osigurava da link nema defaultnu plavu boju ili podcrtavanje */
.no-underline {
  text-decoration: none;
}
</style>