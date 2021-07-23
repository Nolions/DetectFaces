# DetectFaces

## Face

| Method                  | Describe                       |
| ----------------------- | ------------------------------ |
| boundingBox             | 人臉的軸對齊邊界矩形              |
| trackingId              | 連部追踪ID(需設置enableTracking) |
| rightEyeOpenProbability | 右眼睜開的機率(0.0 ~ 1.0)        |
| leftEyeOpenProbability  | 左眼睜開的機率(0.0 ~ 1.0)        |
| headEulerAngleY         | 人臉圍繞圖像水平軸的旋轉           |
| headEulerAngleY         | 人臉圍繞圖像垂直軸的旋轉           |
| headEulerAngleZ         | 面部圍繞指向圖像外的軸的旋轉        |
| smilingProbability      | 微笑機率                        |
| allLandmarks            | 臉部地標                        |
| allContours             | 所有檢測到的臉部輪廓              |

### Contour types for face

| Constants            | value | Describe |
| -------------------- | ----- | ------------- |
| FACE                 | 1     | 主體面部輪廓    |
| LEFT_EYEBROW_TOP     | 2     | 左眉毛的頂部輪廓 |
| LEFT_EYEBROW_BOTTOM  | 3     | 左眉毛的底部輪廓 |
| RIGHT_EYEBROW_TOP    | 4     | 右眉毛的頂部輪廓 |
| RIGHT_EYEBROW_BOTTOM | 5     | 右眉毛的底部輪廓 |
| LEFT_EYE             | 6     | 左眼腔的輪廓    |
| RIGHT_EYE            | 7     | 右眼腔的輪廓    |
| UPPER_LIP_TOP        | 8     | 上唇的頂部輪廓  |
| UPPER_LIP_BOTTOM     | 9     | 上唇的底部輪廓  |
| LOWER_LIP_TOP        | 10    | 下唇的頂部輪廓  |
| LOWER_LIP_BOTTOM     | 11    | 下唇的底部輪廓  |
| NOSE_BRIDGE          | 12    | 鼻樑的輪廓     |
| NOSE_BOTTOM          | 13    | 鼻樑的輪廓     |
| LEFT_CHEEK           | 14    | 左臉頰中心     |
| RIGHT_CHEEK          | 15    | 右臉頰中心     |

### Nested Class Summary
