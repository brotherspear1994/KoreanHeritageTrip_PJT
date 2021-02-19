<template>
  <v-footer
    color="navy"
    padless
  >
    <v-row
      justify="center"
      no-gutters
    >
      <v-btn
        v-for="link in links"
        :key="link"
        color="white"
        text
        rounded
        class="my-2"
      >
        {{ link }}
      </v-btn>
    <v-card-text class="text-center py-1">
      <v-btn
          v-for="icon in icons"
          :key="icon"
          class="mx-4 white--text"
          icon
        >
          <v-icon size="24px">
            {{ icon }}
          </v-icon>
        </v-btn>
    </v-card-text>
      <v-col
        class="navy py-4 text-center white--text"
        cols="12"
      >
        {{ new Date().getFullYear() }} — <strong>SSAFY</strong>
      </v-col>
    </v-row>
  </v-footer>
</template>

<script>    
export default {
    data: () => ({
      links: [
        'Home',
        'About Us',
        'Team',
      ],
      icons: [
        'mdi-facebook',
        'mdi-twitter',
        'mdi-instagram',
      ],
    }),
  }
</script>

<style>

</style>  