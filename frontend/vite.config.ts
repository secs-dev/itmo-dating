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
    mkcert({
      savePath: './.cert',
      force: true,
      hosts: ["itmodating.3utilities.com", "127.0.0.1"]
    })
  ],
  publicDir: './public',
  server: {
    host: true,
    port: 443,
    https: {
      cert: './.cert/cert.pem',
      key: './.cert/dev.pem'
    }
  },
  preview: {
    strictPort: true,
    port: 443,
    https: {
      cert: './.cert/cert.pem',
      key: './.cert/dev.pem'
    }
    // https: {
    //   key: fs.readFileSync('./.cert/le-privkey.pem'),
    //   cert: fs.readFileSync('./.cert/le-cert.pem'),
    //   ca: fs.readFileSync('./.cert/le-chain.pem'),
    // },
  },
})
