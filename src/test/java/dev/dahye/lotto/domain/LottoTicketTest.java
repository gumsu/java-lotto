package dev.dahye.lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("로또 티켓")
class LottoTicketTest {

    @Test
    @DisplayName("로또 티켓은 6개인 경우 정상적으로 생성된다.")
    void lotto_number() {
        assertThat(LottoTicket.manualIssued(Arrays.asList(1, 2, 3, 4, 5, 6))).isNotNull();
    }

    @Test
    @DisplayName("로또 티켓은 6개가 아닌 경우 IllegalArgument Exception throw")
    void validate_lotto_number_size() {
        assertThatThrownBy(() -> LottoTicket.manualIssued(Arrays.asList(1, 2, 3, 4, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("로또 티켓에는 중복된 숫자가 없는 6자리 숫자여야 합니다.");
    }

    @Test
    @DisplayName("로또 티켓에 중복된 숫자가 들어올 경우 IllegalArgument Exception throw")
    void validate_lotto_number_duplicate() {
        assertThatThrownBy(() -> LottoTicket.manualIssued(Arrays.asList(1, 1, 3, 4, 5, 6)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("로또 티켓에는 중복된 숫자가 없는 6자리 숫자여야 합니다.");
    }

    @ParameterizedTest(name = "유효하지 않은 숫자 = {0}")
    @ValueSource(ints = {0, 50})
    @DisplayName("로또 티켓에 유효하지 않은 숫자가 있는 경우 IllegalArgument Exception throw")
    void validate_lotto_number_range(int invalidLottoNumber) {
        List<Integer> validLottoNumbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        validLottoNumbers.add(invalidLottoNumber);

        assertThrows(IllegalArgumentException.class, () -> LottoTicket.manualIssued(validLottoNumbers),
                "유효하지 않은 로또 번호입니다.");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("로또 수동 발행 시 null인 경우 IllegalArgument Exception throw")
    void validate_lotto_number_null(List<Integer> lottoNumbers) {
        assertThatThrownBy(() -> LottoTicket.manualIssued(lottoNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("lottoNumbers는 null이거나 빈 값일 수 없습니다.");
    }
}
