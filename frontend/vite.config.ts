import { defineConfig } from 'vite'
import tsconfigPaths from 'vite-tsconfig-paths'
import react from '@vitejs/plugin-react-swc'
import mkcert from 'vite-plugin-mkcert'
import fs from 'fs'

export default defineConfig({
  base: '/itmo-dating-mini-app',
  plugins: [
    react(),
    tsconfigPaths(),
    //dev certificate
    //  mkcert(),
  ],
  publicDir: './public',
  server: {
    host: true,
  },
  preview: {
    strictPort: true,
    port: 443,
    https: {
      key: fs.readFileSync('./.cert/privkey.pem'),
      cert: fs.readFileSync('./.cert/cert.pem'),
      ca: fs.readFileSync('./.cert/chain.pem'),
    },
  },
})
