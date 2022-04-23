import { NzFormatEmitEvent, NzTreeNode, NzTreeNodeOptions } from 'ng-zorro-antd/tree';
export class TreeNodes {
    activated!: NzTreeNode;
    request!: any[];
    nodes!: any[];
    checkable!: boolean;
    checkedKeys!: any[];
    selectedKeys!: any[];
    _rootNode!: any;
    constructor(checkable: boolean) {
        this.checkable = checkable;
        this.checkedKeys = [];
        this.selectedKeys = [];
    }

    init(treeAttrs: any) {
        this._rootNode = { title: treeAttrs.rootNode.title, key: treeAttrs.rootNode.key, expanded: true, isLeaf: false };
        this.request = treeAttrs.nodes;
    }

    build(): any[] {
        return this.buildTree(this._rootNode);
    }

    buildTree(rootNode: any): any[] {
        let treeNodes: any[] = [];
        for (let node of this.request) {
            if (node.key != rootNode.key && node.parentKey == rootNode.key) {
                let treeNode = { title: node.title, key: node.key, expanded: false, isLeaf: true };
                this.buildTree(treeNode);
                treeNodes.push(treeNode);
                rootNode.isLeaf = false;
            }
        }
        rootNode.children = treeNodes;
        return [rootNode];
    }
}
