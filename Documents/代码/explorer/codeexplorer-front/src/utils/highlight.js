/* 代码颜色高亮 */

import hljs from 'highlight.js'
// import 'highlight.js/styles/vs2015.css'

function highlightCodeAndShowLineNumbers(el, binding) {
  const blocks = el.querySelectorAll('pre code')
  blocks.forEach((block) => {
    hljs.highlightElement(block)
    if (binding.value && binding.value.showLineNumbers) {
      const code = block.innerText
      const lines = code.split('\n')
      const lineNumberContainer = document.createElement('span')
      lineNumberContainer.className = 'number-container'

      lines.forEach((line, index) => {
        const lineNumber = index + 1
        const lineNumberElement = document.createElement('span')
        lineNumberElement.className = 'line-number'
        lineNumberElement.textContent = lineNumber
        lineNumberContainer.appendChild(lineNumberElement)
      })
      block.parentNode.insertBefore(lineNumberContainer, block)
    } else {
      const lineNumberContainer = block.previousElementSibling
      if (
        lineNumberContainer &&
        lineNumberContainer.className === 'number-container'
      ) {
        lineNumberContainer.remove()
      }
    }
  })
}

export default highlightCodeAndShowLineNumbers