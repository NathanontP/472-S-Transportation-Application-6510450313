import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    host: '0.0.0.0', // เปิดให้เข้าถึงจากภายนอก
    port: 4173, // ใช้พอร์ต 4173
  },
  preview: {
    host: '0.0.0.0',
    port: 4173, // ✅ เปลี่ยนพอร์ตเป็น 4173
    allowedHosts: ['g1-472.jgogo01.in.th'], // ✅ อนุญาตให้เข้าถึงจาก domain นี้
  },
  define: {
    'process.env.VITE_API_BASE_URL': JSON.stringify(process.env.VITE_API_BASE_URL || )
  }
})
