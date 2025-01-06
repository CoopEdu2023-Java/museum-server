import { Upload, Button, Toast, List } from '@douyinfe/semi-ui';
import { IconUpload } from '@douyinfe/semi-icons';
import { useState } from 'react';
import { FileItem } from '@douyinfe/semi-ui/lib/es/upload';

function Files({ getFilesId }: { getFilesId: (value: string[]) => void }) {
    const action = 'http://localhost:8080/files/upload';
    const initList: FileItem[] = [];
    const [list, updateList] = useState<FileItem[]>(initList);
    const onChange = ({ fileList, currentFile }: { fileList: FileItem[]; currentFile: FileItem }) => {
        console.log('onChange');
        console.log(fileList);
        console.log(currentFile);
        let newFileList = [...fileList]; // spread to get new array
        updateList(newFileList);
    };
    return (
        <Upload multiple action={action} onChange={onChange} fileList={list} beforeUpload={() => { console.log(`beforeUpload: 即将向${action}上传文件`); return true; }}>
            <Button
                style={{ margin: '10px 0 10px 0' }}
                icon={<IconUpload />}
                theme="light"
                maxSize={10240000}
                minSize={20}
                onSizeError={() => Toast.error(`File size invalid`)}
                onSuccess={(responseBody: any) => (getFilesId(responseBody))}
            >
                点击上传
            </Button>
        </Upload>
    );
}

export default Files;
