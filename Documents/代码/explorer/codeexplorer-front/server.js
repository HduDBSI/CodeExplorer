const express = require('express');
const app = express();
const cors = require('cors');

// 允许所有域访问
app.use((req, res, next) => {
    console.log(`Received request: ${req.method} ${req.url}`);
    next(); // Proceed to the next middleware
  });

app.get('/test', (req, res) => {
    res.send('Hello World');
    console.log('Responded to root route'); 
});

app.listen(8998, () => {
    console.log('Server running on port 8998');
});
