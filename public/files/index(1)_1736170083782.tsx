import { Upload } from '@douyinfe/semi-ui';
import { IconPlus } from '@douyinfe/semi-icons';
import { useState } from 'react';
import { FileItem } from '@douyinfe/semi-ui/lib/es/upload';


function Avatar({ getAvatarId, artifactId }: { getAvatarId: (value: string) => void; artifactId: number }) {
    const action = artifactId ? `http://localhost:8080/artifacts/${artifactId}/avatars/add` : '';
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
        <>
            <Upload
                style={{ margin: '10px 0 10px 0' }}
                action={action}
                name='image'
                accept='image/*'
                onChange={onChange}
                beforeUpload={() => { console.log(`beforeUpload: 即将向${action}上传封面, 将要上传到的ARTIFACTID为${artifactId}`); return true; }}
                fileList={list}
                listType="picture"
                onSuccess={(responseBody: any) => (getAvatarId(responseBody))}
            >
                <IconPlus size="extra-large" />
            </Upload>
        </>

    );
}

export default Avatar;