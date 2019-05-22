package com.imooc.course.service;


import com.imooc.course.dto.CourseDTO;
import com.imooc.course.mapper.CourseMapper;
import com.imooc.course.thrift.ServiceProvider;
import com.imooc.course.thrift.user.dto.TeacherDTO;
import com.imooc.course.thrift.user.UserInfo;
import org.apache.dubbo.config.annotation.Service;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;


@Service(version = "1.0.0")
public class CourseServiceImpl implements ICourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ServiceProvider serviceProvider;

    @Override
    public List<CourseDTO> courseList() {
        List<CourseDTO> courseDTOS = courseMapper.listCourse();
        System.out.println(courseDTOS);
        if (courseDTOS != null) {
            for (CourseDTO course : courseDTOS) {
                Integer teacherId = courseMapper.getCourseTeacher(course.getId());
                if (teacherId != null) {
                    try {
                        UserInfo userInfo = serviceProvider.getUserService().getTeacherById(teacherId);
                        course.setTeacher(trans2Teacher(userInfo));
                    } catch (TException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        }
        return courseDTOS;
    }

    private TeacherDTO trans2Teacher(UserInfo userInfo) {
        TeacherDTO teacherDTO = new TeacherDTO();
        BeanUtils.copyProperties(userInfo, teacherDTO);
        return teacherDTO;
    }

}
