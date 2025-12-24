<template>
  <div class="title">
    <monacoDiff
      :oldValue="oldCode"
      :newValue="newCode"
      :oldCloneValue="oldCloneCode"
      :newCloneValue="newCloneCode"
    />
  </div>
</template>

<script>
import monacoDiff from '../../utils/monaco-diff.vue';

export default {
  name: 'App',
  components: {
    monacoDiff
  },
  data() {
    return {
      oldCode: "package googlejam3.p449;\nimport java.io.BufferedReader;\nimport java.io.File;\nimport java.io.FileReader;\nimport java.io.IOException;\n\npublic class A {\n\tpublic static void main(String[] args) {\n\t\tA obj = new A();\n\t\tString filame = \"input.txt\";\n\t\tif (args.length > 0) {\n\t\t\tfilame = args[0];\n\t\t}\n\t\tFile file = new File(filame);\n\t\ttry (BufferedReader br = new BufferedReader(new FileReader(file))) {\n\t\t\tInteger testCasesCount = Integer.parseInt(br.readLine());\n\t\t\tint testCaseNumber = 1;\n\t\t\tfor (String line; (line = br.readLine()) != null;) {\n\t\t\t\tString[] testCase = line.split(\"\\\\s+\");\n\t\t\t\tint row = Integer.parseInt(testCase[0]);\n\t\t\t\tint cells = Integer.parseInt(testCase[1]);\n\t\t\t\tint width = Integer.parseInt(testCase[2]);\n\t\t\t\t//\t\tchar[] visitors = visitorsLine.toCharArray();\n\t\t\t\t//\t\tint total = 0;\n\t\t\t\t//\t\tint friends = 0;\n\t\t\t\t//\n\t\t\t\t//\t\tSystem.out.print(\"Case #\" + testCasesCount + \": \");\n\t\t\t\t//\t\tfor (int i = 0; i < visitors.length; i++) {\n\t\t\t\t//\t\t\tint shameCount = Character.getNumericValue(visitors[i]);\n\t\t\t\t//\n\t\t\t\t//\t\t\tif (total < i) {\n\t\t\t\t//\n\t\t\t\t//\t\t\t\tint frindsToAdd = i - total;\n\t\t\t\t//\t\t\t\ttotal += frindsToAdd;\n\t\t\t\t//\t\t\t\tfriends += frindsToAdd;\n\t\t\t\t//\t\t\t}\n\t\t\t\t//\t\t\ttotal += shameCount;\n\t\t\t\t//\t\t}\n\t\t\t\t\t\t//System.out.print(row);\n\t\t\t\t\t\t//System.out.print(cells);\n\t\t\t\t\t\t//System.out.println(width);\n\t\t\t\t\n\t\t\t\t\t\tint row = (cells - width) / w;\n\t\t\tif (cells % width == 0) row += width;\n\t\t\telse row += width + 1;\n\t\t\t\n\t\t\tres = res*row;\n\t\t\t\n\t\t\t//System.out.println();\n\t\t\t}\n\t\t} catch (IOException e) {\n\t\t\te.printStackTrace();\n\t\t}\n\n\t}\n}\n",
      oldCloneCode:"int row = Integer.parseInt(testCase[0]);\nint cells = Integer.parseInt(testCase[1]);\nint width = Integer.parseInt(testCase[2]);",
      newCode:"package googlejam3.p443;\nimport java.io.BufferedReader;\nimport java.io.IOException;\nimport java.io.InputStreamReader;\n\n\npublic class A {\n\tpublic static void main(String[] args) throws IOException {\n\t\tBufferedReader in = new  BufferedReader(new InputStreamReader(System.in));\n\t\t\n\t\tint t = Integer.parseInt(in.readLine());\n\t\t\n\t\tfor (int i = 1; i <= t; i++) {\n\t\t\tint res = 0;\n\t\t\t// body of the program\n\t\t\t\n\t\t\tString[] next = in.readLine().split(\" \");\n\t\t\tint r = Integer.parseInt(next[0]);\n\t\t\tint c = Integer.parseInt(next[1]);\n\t\t\tint w = Integer.parseInt(next[2]);\n\t\t\t\n\t\t\tint row = (c - w) / w;\n\t\t\tif (c % w == 0) row += w;\n\t\t\telse row += w + 1;\n\t\t\t\n\t\t\tres = r*row;\n\t\t\t\n\t\t\tSystem.out.format(\"Case #%d: %d\\n\", i, res);\n\t\t}\n\t}\n}\n",
      newCloneCode:"int r = Integer.parseInt(next[0]);\n\t\t\tint c = Integer.parseInt(next[1]);\n\t\t\tint w = Integer.parseInt(next[2]);",
    };
  }
};
</script>

<style>
.title {
  width: 100%;
  min-height:100%;
}
</style>
