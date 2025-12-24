//将json变为html并在每行末尾添加<br>

/* 输出示例
        content: `
                     Train list: 353398<br>
                        Valid list: 13695<br>
                        Test list: 13695<br>
                        Add all data to RAM...<br>
                        Test list 27390<br>
                        Test list 166<br>
                        0it [00:00, ?it/s]<br>
                `
*/
export function convertStderrToHtml(stderrText) {
    // 将每一行都添加 <br> 标签
    let html = stderrText
        .split('\n') // 按行分割
        .map(line => line + '<br>') // 为每行添加 <br> 标签
        .join('');
    return html;
}