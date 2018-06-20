package com.newcoder.toutiao.service;

import com.newcoder.toutiao.dao.NewsDAO;
import com.newcoder.toutiao.model.News;
import com.newcoder.toutiao.model.User;
import com.newcoder.toutiao.util.ToutiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

/**
 * created by zsj in 11:20 2018/5/13
 * description:
 **/
@Service
public class NewsService {
    @Autowired
    private NewsDAO newsDAO;

    public List<News> getLatestNews(int userId, int offset, int limit) {
        return newsDAO.selectByUserIdAndOffset(userId, offset, limit);
    }

    /**
     * 保存图片到本地
     * @param file
     * @return
     * @throws Exception
     */
    public String saveImage(MultipartFile file) throws Exception {
        //获取file原来的名字，并且获取后缀名
        int docPos = file.getOriginalFilename().lastIndexOf(".");
        if (docPos < 0) {
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(docPos+1);
        //发现后缀名不合法则返回null
        if (!ToutiaoUtil.isFileAllowed(fileExt)) {
            return null;
        }
        //使用UUID生成一个随机的文件名+后缀名给上传的文件
        String fileName = UUID.randomUUID().toString().replaceAll("-","")+"."+fileExt;
        //将上传的文件copy到指定的目录下，三个参数，一个文件输入流，一个输出流，一个选项（覆盖或其他）
        Files.copy(file.getInputStream(),new File(ToutiaoUtil.IMAGE_DIR+fileName).toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        //返回一个上传的文件夹
        return ToutiaoUtil.TOUTIAO_DOMAIN + "image?name=" + fileName;
    }

    //添加一条资讯
    public int addNews(News news) {
        newsDAO.addNews(news);
        return news.getUserId();
    }

    //通过newsId选择一条资讯
    public News getById(int newsId) {
        return newsDAO.getById(newsId);
    }

    //更新新闻评论数量
    public int updateCommentCountById(int id, int commentCount) {
        return newsDAO.updateCommentCountById(id, commentCount);
    }

    //更新新闻喜欢数量
    public int updateLikeCountById(int id, int likeCount) {
        return newsDAO.updateLikeCountById(id, likeCount);
    }




}
