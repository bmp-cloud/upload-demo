package org.example.upload_demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@MultipartConfig
@WebServlet(name = "uploaderServlet", value = "/uploader")
public class Uploader extends HttpServlet {

    private Storage storage;

    public void init() {
        // Google Cloud Storage 클라이언트 초기화
        storage = StorageOptions.getDefaultInstance().getService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 응답 콘텐츠 유형 설정
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {
            // 업로드된 파일 받기
            Part filePart = request.getPart("file");

            if (filePart == null || filePart.getSize() == 0) {
                out.println("<html><body>");
                out.println("<h1>파일이 업로드되지 않았습니다.</h1>");
                out.println("</body></html>");
                return;
            }

            String fileName = filePart.getSubmittedFileName();
            InputStream fileContent = filePart.getInputStream();

            // 업로드할 버킷 이름 (실제 버킷 이름으로 변경하세요)
            String bucketName = "YOUR_BUCKET_NAME";

            // BlobId 및 BlobInfo 생성
            BlobId blobId = BlobId.of(bucketName, fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(filePart.getContentType())
                    .build();

            // 파일 업로드 (deprecated된 메서드 대신 새로운 메서드 사용)
            storage.createFrom(blobInfo, fileContent);

            out.println("<html><body>");
            out.println("<h1>파일 업로드 성공!</h1>");
            out.println("<p>파일 이름: " + fileName + "</p>");
            out.println("</body></html>");
        } catch (Exception e) {
            // 에러 처리
            e.printStackTrace();
            out.println("<html><body>");
            out.println("<h1>파일 업로드 실패!</h1>");
            out.println("<p>오류 메시지: " + e.getMessage() + "</p>");
            out.println("</body></html>");
        }
    }
}
