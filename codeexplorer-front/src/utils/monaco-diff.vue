<template>
    <div class="title">
        <!-- <div ref="diffContainer" style="height: 100vh;"></div> -->
        <div ref="oldEditorContainer" style="height: 100vh;width:50%;"></div>
        <div ref="newEditorContainer" style="height: 100vh;width: 50%;"></div>
    </div>
</template>

<script>
import * as monaco from 'monaco-editor';
import debounce from "lodash/debounce";

export default {
    name: 'MonacoDiffEditor',
    props: {
        oldValue: {
            type: String,
            required: true
        },
        newValue: {
            type: String,
            required: true
        },
        oldCloneValue: {
            type: String,
            required: true
        },
        newCloneValue: {
            type: String,
            required: true
        },

    },
    data() {
        return {
            old: '',
            new: '',
            decorations: [],  // 存储 decoration ID
            
        }

    },
    mounted() {
        // this.createDiffEditor();
        this.createEditor(); // 先创建编辑器
        this.$nextTick(() => { // 等待下一个 Vue 更新周期，确保编辑器已经创建
            this.create();
            // this.initColor();
        });
    },
    methods: {
        create() {
            const json = {
                code1_content: this.oldValue,
                code1_clone_content: this.oldCloneValue,
                code2_content: this.newValue,
                code2_clone_content: this.newCloneValue,
            };

            // 获取 JSON 中的内容
            const code1Content = json.code1_content;
            const code1CloneContent = json.code1_clone_content;
            const code2Content = json.code2_content;
            const code2CloneContent = json.code2_clone_content;
            // 将 code1_clone_content 转义为正则表达式
            const escapedContent1 = code1CloneContent.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')  // 先进行正则转义
                .replace(/\s+/g, '\\s*'); // 允许空格、换行变化
            const escapedContent2 = code2CloneContent.replace(/[.*+?^${}()|[\]\\]/g, '\\$&').replace(/\s+/g, '\\s*');
            // 创建正则表达式
            this.old = new RegExp(escapedContent1, 'g'); // 's' 模式允许点号 (.) 匹配换行符
            this.new = new RegExp(escapedContent2, 'g');
            // 高亮匹配的文本
            // this.highlightMatches(code1Content, this.old, this.oldEditor);
            this.highlightMatches(code2Content, this.new, this.newEditor);
            this.highlightMatches(code1Content, this.old, this.oldEditor);
            // 进行匹配
            if (this.old.test(code1Content)) {
                console.log("匹配成功!", this.old);
            } else {
                console.log("未匹配到内容。", this.old, "原内容", code1Content);
            }
            if (this.new.test(code2Content)) {
                console.log("匹配成功!", this.new);
            } else {
                console.log("未匹配到内容。");
            }
        },
        initColor() {
            console.log("进来了", this.old, this.new);
            monaco.languages.setMonarchTokensProvider('java', {
                ignoreCase: true,
                tokenizer: {//需要什么颜色，就在里面用正则匹配
                    root: [
                        [
                            this.old,
                            { token: 'keyword' }
                        ], //蓝色
                        [
                            this.new,
                            { token: 'string' }
                        ], //红色



                    ]
                },
            })
            // 触发编辑器重新渲染
            this.oldEditor.updateOptions({ readOnly: false });
            this.oldEditor.updateOptions({ readOnly: true });

            this.newEditor.updateOptions({ readOnly: false });
            this.newEditor.updateOptions({ readOnly: true });
        },

        // highlightMatches(content, regex, editor) {
        //     const matches = [];
        //     let match;
        //     while ((match = regex.exec(content)) !== null) {
        //         // 匹配的内容的起始位置和结束位置
        //         console.log('位置信息', match.index,match[0].length)
        //         const range = new monaco.Range(1, match.index + 1, 1, match.index + match[0].length + 1);
        //         matches.push({
        //             range,
        //             options: {
        //                 isWholeLine: false,
        //                 className: 'custom-match-background',  // 高亮的样式
        //             }
        //         });
        //     }
        //     // 使用 deltaDecorations 高亮匹配的内容
        //     editor.deltaDecorations([], matches);
        // },
        // createDiffEditor() {
        //     this.diffEditor = monaco.editor.createDiffEditor(this.$refs.diffContainer, {
        //         theme: 'vs',
        //         language: 'java', // 设置语言为 Java
        //         diffAlgorithm: 'none'
        //     });

        //     // 不需要 JSON.parse，因为我们现在是比较代码
        //     const oldModel = monaco.editor.createModel(this.oldValue, 'java');
        //     const newModel = monaco.editor.createModel(this.newValue, 'java');

        //     this.diffEditor.setModel({
        //         original: oldModel,
        //         modified: newModel
        //     });
        // }
        highlightMatches(content, regex, editor) {
            const matches = [];
            let match;

            while ((match = regex.exec(content)) !== null) {
                const start = editor.getModel().getPositionAt(match.index);
                const end = editor.getModel().getPositionAt(match.index + match[0].length);

                console.log(`匹配开始: 行 ${start.lineNumber}, 列 ${start.column}`);
                console.log(`匹配结束: 行 ${end.lineNumber}, 列 ${end.column}`);

                const range = new monaco.Range(start.lineNumber, start.column, end.lineNumber, end.column);

                matches.push({
                    range,
                    options: {
                        isWholeLine: true,
                        className: "added",
                    }
                });
                // editor.createDecorationsCollection(
                //     visibleDecorations.map((range, index) => ({
                //         id: index,
                //         range,
                //         options: { inlineClassName }
                //     }))
                // );

            }

            // 应用高亮
            this.decorations = editor.deltaDecorations(this.decorations || [], matches);
        },
        createEditor() {
            // 创建两个独立的编辑器，而不是 diff 编辑器
            this.oldEditor = monaco.editor.create(this.$refs.oldEditorContainer, {
                theme: 'vs',
                language: 'java',
                value: this.oldValue,
                readOnly: true  // 设置为只读
            });

            this.newEditor = monaco.editor.create(this.$refs.newEditorContainer, {
                theme: 'vs',
                language: 'java',
                value: this.newValue,
                readOnly: true  // 设置为只读
            });
        }

    },
    beforeDestroy() {
        if (this.diffEditor) {
            this.diffEditor.dispose();
        }
        if (this.oldEditor) {
            this.oldEditor.dispose();
        }
        if (this.newEditor) {
            this.newEditor.dispose();
        }
    }
};
</script>

<style>
/* 你可以在这里添加组件的样式 */
.title {
    width: 100%;
    min-height: 100%;
    display: flex;
}

.added {
    background-color:#ff000033 !important; /* 绿色背景 */
    display: block;
    width: 100%;
}
</style>