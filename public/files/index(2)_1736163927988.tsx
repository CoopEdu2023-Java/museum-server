import { Select } from '@douyinfe/semi-ui';
import http from '../../http';

function Competency({ getCompetency }: { getCompetency: (value: string) => void }) {

    let list;

    async function fetchData() {
        try {
            const response = await http.get('http://localhost:8080/competencies');
            const result = await response.data;
            list = result;
        } catch (error) {
            console.error(error);
        }
    }

    fetchData();

    // 需要和后端沟通指定数据格式
    list = [
        { value: 'douyin', label: '抖音', otherKey: 0 },
        { value: 'ulikecam', label: '轻颜相机', disabled: true, otherKey: 1 },
        { value: 'jianying', label: '剪映', otherKey: 2 },
        { value: 'toutiao', label: '今日头条', otherKey: 3 },
    ];

    return (
        <Select
            placeholder="选择作品分类"
            style={{ width: 180, margin: '10px 0 10px 0' }}
            optionList={list}
            onSelect={(value: any) => (getCompetency(value))}
        >
        </Select>
    );
}

export default Competency;