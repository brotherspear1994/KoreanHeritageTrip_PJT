import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';

import colors from 'vuetify/lib/util/colors';

Vue.use(Vuetify);

export default new Vuetify({
    icons: {
      iconfont: 'mdiSvg', // 'mdi' || 'mdiSvg' || 'md' || 'fa' || 'fa4' || 'faSvg'
    },
    theme: {
        themes: {
          light: {
            blueGreyDarken4: colors.blueGrey.darken4, // #E53935
            brownLighten4: colors.brown.lighten4, 

            navy: '#24303F',
            brown4: '#513F42',
            brown3: '#CDB7A9',
            brown2: '#E2D6CE',
            brown1: '#F1F0E8',
            grey: '#C2C7CD',

            // secondary: colors.red.lighten4, // #FFCDD2
            // accent: colors.indigo.base, // #3F51B5
          },
        },
      },
});
