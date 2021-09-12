package cn.tedu.straw.search.repository;

import cn.tedu.straw.search.vo.QuestionVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends ElasticsearchRepository<QuestionVo, Integer> {
    /**
     * 如果想看到查询结果需要满足下面条件
     * 如果标题中包含title字样或者内容包含title字样
     * 并且
     * 题目的发布人是登录用户或题目本身是公开的
     * 满足上面条件就查询出来
     *
     * @param title
     * @param content
     * @param userId
     * @param pageable
     * @return
     */
    @Query("{\n" +
            "    \"bool\": {\n" +
            "      \"must\": [{\n" +
            "        \"bool\": {\n" +
            "          \"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"content\": \"?1\"}}]\n" +
            "        }\n" +
            "      }, {\n" +
            "        \"bool\": {\n" +
            "          \"should\": [{\"term\": {\"publicStatus\": 1}}, {\"term\": {\"userId\": ?2}}]\n" +
            "        }\n" +
            "      }]\n" +
            "    }\n" +
            "}")
    public Page<QuestionVo> queryAllByParams(String title, String content, Integer userId, Pageable pageable);


}
