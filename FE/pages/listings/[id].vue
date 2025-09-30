<template>
  <div class="bg-gray-900 text-white min-h-screen">
    <UContainer class="py-12">
      <div v-if="listingData">
        <header class="mb-8">
          <h1 class="text-4xl md:text-5xl font-bold tracking-tight text-white mb-2">
            {{ listingData.title }}
          </h1>
          <div class="flex items-center space-x-4 text-gray-300">
            <div class="flex items-center">
              <UIcon v-for="i in 5" :key="i" name="i-heroicons-star-solid" 
                     :class="i <= listingData.rating ? 'text-yellow-400' : 'text-gray-600'" 
                     class="w-5 h-5" />
            </div>
            <div class="flex items-center">
              <UIcon name="i-heroicons-map-pin" class="w-5 h-5 mr-2" />
              <span>{{ listingData.location }}</span>
            </div>
          </div>
        </header>

        <div class="mb-12">
          <UCarousel 
            v-slot="{ item }" 
            :items="listingData.images"
            :ui="{ item: 'basis-full' }"
            class="rounded-lg overflow-hidden border border-gray-700"
            arrows
            indicators
          >
            <img :src="item" class="w-full h-96 object-cover" draggable="false">
          </UCarousel>
        </div>

        <div class="grid grid-cols-1 lg:grid-cols-3 gap-12">
          <div class="lg:col-span-2">
            <h2 class="text-2xl font-bold border-b border-gray-700 pb-2 mb-4">
              O objektu
            </h2>
            <p class="text-gray-300 leading-relaxed mb-8">
              {{ listingData.description }}
            </p>

            <h3 class="text-xl font-bold mb-4">Sadržaji</h3>
            <ul class="grid grid-cols-2 gap-x-8 gap-y-2">
              <li v-for="amenity in listingData.amenities" :key="amenity" class="flex items-center">
                <UIcon name="i-heroicons-check-circle" class="w-5 h-5 text-emerald-500 mr-3" />
                <span class="text-gray-300">{{ amenity }}</span>
              </li>
            </ul>
          </div>

          <div>
            <UCard class="bg-gray-800/75 border border-gray-700">
              <div class="text-center space-y-4">
                <p class="text-lg text-gray-400">Cijena po noćenju</p>
                <p class="text-4xl font-bold text-white">
                  €{{ listingData.pricePerNight }}
                </p>
                <UButton 
                  label="Rezerviraj sada"
                  size="xl" 
                  block
                  class="bg-emerald-500 hover:bg-emerald-600 text-gray-900 font-bold"
                />
              </div>
            </UCard>
          </div>
        </div>
        
        <div class="mt-16">
          <h2 class="text-2xl font-bold border-b border-gray-700 pb-2 mb-6">
            Recenzije i komentari ({{ listingData.comments.length }})
          </h2>
          <div class="space-y-6">
            <div v-for="(comment, index) in listingData.comments" :key="index"
                 class="p-4 border border-gray-800 rounded-lg bg-gray-800/50">
              <div class="flex items-center justify-between mb-2">
                <p class="font-semibold text-white">{{ comment.author }}</p>
                <div class="flex items-center">
                  <UIcon v-for="i in 5" :key="i" name="i-heroicons-star-solid" 
                         :class="i <= comment.rating ? 'text-yellow-400' : 'text-gray-600'" 
                         class="w-4 h-4" />
                </div>
              </div>
              <p class="text-sm text-gray-400 mb-1">{{ comment.date }}</p>
              <p class="text-gray-300">{{ comment.text }}</p>
            </div>
          </div>
        </div>

      </div>
      <div v-else>
        <p>Učitavanje podataka o listingu...</p>
      </div>
    </UContainer>
  </div>
</template>

<script setup lang="ts">
// Dohvaćamo ID iz URL-a
const route = useRoute();
const id = route.params.id;

// --- DUMMY PODACI ---
// U stvarnoj aplikaciji, ovdje biste dohvatili podatke s API-ja koristeći ID:
// const { data: listingData } = await useFetch(`/api/listings/${id}`);

const listingData = {
  id: id,
  title: 'Hotel Ambasador',
  location: 'Trumbićeva obala 18, 21000 Split, Croatia',
  rating: 5,
  pricePerNight: 250,
  images: [
    'https://cf.bstatic.com/xdata/images/hotel/max1024x768/388373453.jpg?k=2871f34b868b9a1a5802c70d6160d5b5b542095f5195c6b0c2c3e414c2789434&o=&hp=1',
    'https://cf.bstatic.com/xdata/images/hotel/max1024x768/388373461.jpg?k=b0c897f191b702755e128185799738f615f3358f2882f06b65377f078a6358c5&o=&hp=1',
    'https://cf.bstatic.com/xdata/images/hotel/max1024x768/388373475.jpg?k=0942c4b8e21966d9b9b4f23b6b694b790d2e82512f451f2249ac89b88d227f42&o=&hp=1',
    'https://cf.bstatic.com/xdata/images/hotel/max1024x768/388373466.jpg?k=6b63f2560e980a3161c565576a08f583f7a117b8be51525a1f64606c49a37c44&o=&hp=1',
  ],
  description: 'Hotel Ambasador u Splitu nudi krovni bazen, spa sadržaje, saunu, fitness centar, sunčanu terasu, restoran, bar i besplatan WiFi. Gosti mogu uživati u modernom restoranu koji poslužuje mediteransku kuhinju s vegetarijanskim, veganskim, bezglutenskim i bezmliječnim opcijama.',
  amenities: [
    'Krovni bazen',
    'Spa i wellness centar',
    'Besplatan WiFi',
    'Restoran i bar',
    'Fitness centar',
    'Pogled na more',
    'Klimatizacija',
    'Privatna kupaonica'
  ],
  comments: [
    { author: 'Ana Kovačić', rating: 5, date: '25. rujna 2025.', text: 'Apsolutno savršeno! Pogled je nevjerojatan, a osoblje izuzetno ljubazno. Bazen na krovu je highlight boravka.' },
    { author: 'Marko Jurić', rating: 4, date: '15. rujna 2025.', text: 'Vrlo dobar hotel na odličnoj lokaciji. Soba je bila prostrana i čista. Jedina zamjerka je što je doručak mogao biti malo raznovrsniji.' },
    { author: 'Ivana Babić', rating: 5, date: '02. rujna 2025.', text: 'Preporučujem svima! Moderno uređen, čist, s fantastičnim spa centrom. Vraćamo se sigurno.' }
  ]
};

// Postavljanje naslova stranice dinamički
useHead({
  title: `${listingData.title} | Detalji`
})
</script>

<style scoped>
/* Dodatni stilovi po potrebi */
</style>