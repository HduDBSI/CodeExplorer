// 使用 Web Workers 处理异步请求，这个脚本会处理请求的发送和接收。
// self.importScripts('https://cdnjs.cloudflare.com/ajax/libs/idb/6.0.7/idb.min.js'); // 导入 IDB 库，便于使用 IndexedDB
import { openDB } from 'idb';

const dbPromise = openDB('my-database', 1, {
    upgrade(db) {
        if (!db.objectStoreNames.contains('my-store')) {
            db.createObjectStore('my-store');
        }
    }
});

self.addEventListener('message', async (e) => {
    const postData = e.data;

    try {
        const db = await dbPromise;
        await db.put('my-store', postData, 'my-key');
        const storedData = await db.get('my-store', 'my-key');
        self.postMessage({ storedData });
    } catch (error) {
        self.postMessage({ error: error.message });
    }
});