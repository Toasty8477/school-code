/**
This Source Code Form is subject to the terms of the Mozilla Public
License, v. 2.0. If a copy of the MPL was not distributed with this
file, You can obtain one at https://mozilla.org/MPL/2.0/.
 **/

package com.example.swe2710project.presentation

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.SwitchButton
import androidx.wear.compose.material3.Text

val milwaukeeLogo: ImageVector
    get() {
        _milwaukeeLogo = ImageVector.Builder(
            name = "milwaukeeLogo",
            defaultWidth = 956.69287.dp,
            defaultHeight = 467.00787.dp,
            viewportWidth = 956.69287f,
            viewportHeight = 467.00787f
        ).apply {
            group {
                path(
                    fill = SolidColor(Color.White)
                ) {
                    moveTo(1495.25f, -1674.9702f)
                    lineTo(1261.8949f, -1586.334f)
                    lineTo(1275.4644f, -1615.5947f)
                    lineTo(959.22182f, -1507.5241f)
                    lineTo(976.73515f, -1543.7539f)
                    lineTo(609.50675f, -1402.6112f)
                    lineTo(885.24138f, -1471.8289f)
                    lineTo(862.64786f, -1428.3291f)
                    lineTo(1204.492f, -1555.6362f)
                    lineTo(1184.9566f, -1517.7179f)
                    lineTo(1495.25f, -1674.9702f)
                    close()
                }
            }
            group {
                path(
                    fill = SolidColor(Color.White)
                ) {
                    moveTo(1443.0943f, -1782.3055f)
                    curveTo(1444.7487f, -1782.3055f, 1446.3029f, -1782.4392f, 1446.3029f, -1784.4445f)
                    curveTo(1446.3029f, -1786.0662f, 1444.7989f, -1786.3496f, 1443.4286f, -1786.3496f)
                    lineTo(1440.7548f, -1786.3496f)
                    lineTo(1440.7548f, -1782.3055f)
                    lineTo(1443.0943f, -1782.3055f)
                    close()
                    moveTo(1440.7548f, -1774.986f)
                    lineTo(1438.7494f, -1774.986f)
                    lineTo(1438.7494f, -1788.0207f)
                    lineTo(1443.7461f, -1788.0207f)
                    curveTo(1446.8042f, -1788.0207f, 1448.3249f, -1786.8844f, 1448.3249f, -1784.3269f)
                    curveTo(1448.3249f, -1781.9886f, 1446.8878f, -1780.9525f, 1444.9493f, -1780.7346f)
                    lineTo(1448.6425f, -1774.986f)
                    lineTo(1446.4533f, -1774.986f)
                    lineTo(1442.9941f, -1780.6344f)
                    lineTo(1440.7548f, -1780.6344f)
                    lineTo(1440.7548f, -1774.986f)
                    close()
                    moveTo(1443.1445f, -1772.0782f)
                    curveTo(1448.2581f, -1772.0782f, 1452.3189f, -1776.0889f, 1452.3189f, -1781.5368f)
                    curveTo(1452.3189f, -1786.8844f, 1448.2581f, -1790.9285f, 1443.1445f, -1790.9285f)
                    curveTo(1437.964f, -1790.9285f, 1433.9199f, -1786.8844f, 1433.9199f, -1781.5368f)
                    curveTo(1433.9199f, -1776.0889f, 1437.964f, -1772.0782f, 1443.1445f, -1772.0782f)
                    moveTo(1431.6305f, -1781.5368f)
                    curveTo(1431.6305f, -1788.0207f, 1436.8945f, -1792.8175f, 1443.1445f, -1792.8175f)
                    curveTo(1449.3109f, -1792.8175f, 1454.5749f, -1788.0207f, 1454.5749f, -1781.5368f)
                    curveTo(1454.5749f, -1774.986f, 1449.3109f, -1770.1732f, 1443.1445f, -1770.1732f)
                    curveTo(1436.8945f, -1770.1732f, 1431.6305f, -1774.986f, 1431.6305f, -1781.5368f)
                }
            }
            group {
                path(
                    fill = SolidColor(Color.White)
                ) {
                    moveTo(815.43874f, -1744.7561f)
                    lineTo(774.98095f, -1710.5984f)
                    curveTo(774.98095f, -1710.5984f, 758.88808f, -1728.6799f, 757.63474f, -1730.0837f)
                    curveTo(756.231f, -1728.8805f, 723.12613f, -1700.8057f, 723.12613f, -1700.8057f)
                    lineTo(727.83868f, -1747.4139f)
                    lineTo(609.42319f, -1647.6307f)
                    lineTo(690.28864f, -1686.4514f)
                    lineTo(669.51663f, -1512.1203f)
                    curveTo(669.51663f, -1512.1203f, 694.28261f, -1493.8048f, 696.33809f, -1492.4171f)
                    curveTo(698.42699f, -1509.0454f, 718.41357f, -1668.7376f, 719.4998f, -1677.4261f)
                    lineTo(719.56664f, -1677.7777f)
                    lineTo(742.97902f, -1697.5303f)
                    lineTo(747.95896f, -1692.6172f)
                    lineTo(729.12545f, -1533.5935f)
                    curveTo(729.12545f, -1533.5935f, 754.2925f, -1514.8101f, 755.89677f, -1513.7246f)
                    curveTo(756.0806f, -1515.2954f, 772.94219f, -1659.4127f, 776.95287f, -1693.787f)
                    lineTo(800.21485f, -1713.4728f)
                    lineTo(805.16136f, -1708.5771f)
                    lineTo(786.97959f, -1553.5132f)
                    lineTo(813.75091f, -1533.778f)
                    lineTo(835.64257f, -1722.4968f)
                    lineTo(815.43874f, -1744.7561f)
                    close()
                }
            }
            group {
                path(
                    fill = SolidColor(Color.White)
                ) {
                    moveTo(881.16385f, -1709.8792f)
                    lineTo(853.22274f, -1705.1332f)
                    lineTo(849.76353f, -1677.7603f)
                    lineTo(877.67122f, -1683.041f)
                    lineTo(881.16385f, -1709.8792f)
                    close()
                    moveTo(1031.3473f, -1698.3979f)
                    lineTo(1020.1174f, -1611.4677f)
                    lineTo(1013.9175f, -1604.8153f)
                    lineTo(1003.4062f, -1613.5398f)
                    lineTo(1013.8507f, -1695.291f)
                    lineTo(985.89288f, -1688.4889f)
                    lineTo(975.13088f, -1603.4289f)
                    lineTo(966.87555f, -1592.4329f)
                    lineTo(958.05204f, -1600.2364f)
                    lineTo(968.54667f, -1685.8325f)
                    lineTo(940.48858f, -1679.9662f)
                    lineTo(931.1136f, -1602.1254f)
                    lineTo(919.48261f, -1586.3166f)
                    lineTo(911.27742f, -1592.9677f)
                    lineTo(929.39235f, -1739.442f)
                    lineTo(902.00271f, -1735.0128f)
                    lineTo(885.1077f, -1594.2886f)
                    lineTo(871.17056f, -1575.1041f)
                    lineTo(864.25213f, -1580.8528f)
                    lineTo(875.8497f, -1666.6306f)
                    lineTo(847.89187f, -1660.9154f)
                    lineTo(834.9407f, -1561.4999f)
                    lineTo(859.55629f, -1542.1991f)
                    lineTo(883.78751f, -1574.937f)
                    lineTo(909.10496f, -1554.6817f)
                    lineTo(933.48659f, -1587.8367f)
                    lineTo(959.62289f, -1565.1108f)
                    lineTo(985.40826f, -1599.2351f)
                    lineTo(1006.3641f, -1580.7859f)
                    lineTo(1042.6107f, -1620.1735f)
                    lineTo(1053.7403f, -1703.2776f)
                    lineTo(1031.3473f, -1698.3979f)
                    close()
                    moveTo(1097.6907f, -1641.247f)
                    lineTo(1086.4107f, -1627.4101f)
                    lineTo(1080.378f, -1632.6066f)
                    lineTo(1089.5691f, -1704.9487f)
                    lineTo(1104.7596f, -1696.8939f)
                    lineTo(1097.6907f, -1641.247f)
                    close()
                    moveTo(1343.3787f, -1744.7882f)
                    lineTo(1356.5972f, -1729.4152f)
                    lineTo(1340.1868f, -1720.3738f)
                    lineTo(1343.3787f, -1744.7882f)
                    close()
                    moveTo(1413.6492f, -1759.1612f)
                    lineTo(1425.815f, -1744.6385f)
                    lineTo(1410.7582f, -1736.1158f)
                    lineTo(1413.6492f, -1759.1612f)
                    close()
                    moveTo(1415.4373f, -1702.1426f)
                    lineTo(1407.3825f, -1708.9099f)
                    lineTo(1409.0369f, -1722.1786f)
                    lineTo(1446.6371f, -1743.4526f)
                    lineTo(1416.3063f, -1781.0194f)
                    lineTo(1388.6493f, -1756.8711f)
                    lineTo(1382.1486f, -1704.6813f)
                    lineTo(1345.3005f, -1684.3953f)
                    lineTo(1336.5271f, -1691.5971f)
                    lineTo(1338.3319f, -1705.6345f)
                    lineTo(1379.2075f, -1728.1786f)
                    lineTo(1346.2697f, -1767.9339f)
                    lineTo(1316.2063f, -1742.3497f)
                    lineTo(1309.856f, -1692.8177f)
                    lineTo(1296.5038f, -1679.8819f)
                    lineTo(1275.4811f, -1721.3925f)
                    lineTo(1322.5732f, -1764.3577f)
                    lineTo(1301.1996f, -1761.2494f)
                    lineTo(1265.7385f, -1731.0529f)
                    lineTo(1274.211f, -1798.6825f)
                    lineTo(1248.5761f, -1795.1397f)
                    lineTo(1234.2713f, -1679.2977f)
                    lineTo(1218.7466f, -1662.2363f)
                    lineTo(1215.0869f, -1665.3111f)
                    lineTo(1224.395f, -1738.8056f)
                    lineTo(1196.7881f, -1732.4727f)
                    lineTo(1188.1651f, -1666.013f)
                    lineTo(1175.8991f, -1651.1734f)
                    lineTo(1170.6685f, -1655.7189f)
                    lineTo(1179.9265f, -1728.6118f)
                    lineTo(1152.1191f, -1723.081f)
                    lineTo(1143.5128f, -1654.9168f)
                    lineTo(1130.6452f, -1638.5384f)
                    lineTo(1124.9968f, -1643.4863f)
                    lineTo(1134.5891f, -1719.6051f)
                    lineTo(1106.8485f, -1713.6399f)
                    lineTo(1106.2135f, -1707.7228f)
                    lineTo(1086.8452f, -1717.065f)
                    lineTo(1059.6227f, -1682.3057f)
                    lineTo(1051.7517f, -1620.2243f)
                    lineTo(1078.0718f, -1597.8474f)
                    lineTo(1102.2362f, -1628.997f)
                    lineTo(1122.9915f, -1611.3661f)
                    lineTo(1145.6519f, -1640.4609f)
                    lineTo(1165.1371f, -1623.3994f)
                    lineTo(1186.5274f, -1651.4408f)
                    lineTo(1206.9151f, -1633.727f)
                    lineTo(1234.522f, -1663.7229f)
                    lineTo(1255.5446f, -1647.0625f)
                    lineTo(1261.8448f, -1697.9808f)
                    lineTo(1283.9537f, -1652.6266f)
                    lineTo(1313.2818f, -1681.2028f)
                    lineTo(1332.3159f, -1665.1774f)
                    lineTo(1389.0169f, -1696.4942f)
                    lineTo(1403.5055f, -1683.9942f)
                    lineTo(1455.5776f, -1714.4407f)
                    lineTo(1456.998f, -1725.6211f)
                    lineTo(1415.4373f, -1702.1426f)
                    close()
                }
            }
        }.build()

        return _milwaukeeLogo!!
    }

private var _milwaukeeLogo: ImageVector? = null

@Composable
fun GenericChip(
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier.fillMaxWidth(),
    onClick: Function0<Unit> = { },
    color: Color = ButtonDefaults.buttonColors().containerColor
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = ""
            )
        },
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = color
        )
    ) {
        Text(
            text = label,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun GenericToggleChip(
    label: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    icon: (@Composable BoxScope.() -> Unit)?,
    enabled: Boolean = true
) {
    SwitchButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        label = label,
        icon = icon,
        enabled = enabled
    )
}